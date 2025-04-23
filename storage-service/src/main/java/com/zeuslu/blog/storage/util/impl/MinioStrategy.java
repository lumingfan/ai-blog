package com.zeuslu.blog.storage.util.impl;

import cn.hutool.core.lang.UUID;
import com.zeuslu.blog.storage.config.StorageConfig;
import com.zeuslu.blog.storage.enums.StorageTypeEnum;
import com.zeuslu.blog.storage.util.StorageStrategy;
import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * MinIO存储策略实现
 * @author lumingfan
 */
@Slf4j
public class MinioStrategy implements StorageStrategy {
    private final StorageConfig.MinioConfig minioConfig;

    public MinioStrategy(StorageConfig.MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
    }

    @Override
    public String uploadFile(MultipartFile file, String dirPrefix) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // 创建MinIO客户端
            MinioClient minioClient = createMinioClient();

            // 检查存储桶是否存在，不存在则创建
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
                log.info("创建存储桶: {}", minioConfig.getBucketName());

                // 设置桶为公开读
                setBucketPolicy(minioClient);
            }

            // 生成文件路径
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            // 日期路径，例如：2023/04/
            String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/"));
            // 文件名：dirPrefix/date/uuid.suffix
            String objectName = dirPrefix + "/" + datePath + UUID.randomUUID() + suffix;

            // 上传文件
            InputStream inputStream = file.getInputStream();
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(objectName)
                            .contentType(file.getContentType())
                            .stream(inputStream, file.getSize(), -1)
                            .build());

            inputStream.close();

            log.info("文件上传成功: {}", objectName);

            // 返回文件访问URL
            return minioConfig.getBaseUrl() + "/" + objectName;

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("MinIO文件上传异常: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty() || !fileUrl.startsWith(minioConfig.getBaseUrl())) {
            return false;
        }
        try {
            MinioClient minioClient = createMinioClient();
            // 从URL提取对象键
            String objectName = fileUrl.substring(minioConfig.getBaseUrl().length() + 1);
            // 删除对象
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(objectName)
                            .build());

            log.info("文件删除成功：{}", objectName);
            return true;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("MinIO文件删除异常: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public StorageTypeEnum getType() {
        return StorageTypeEnum.MINIO;
    }

    /**
     * 创建MinIO客户端
     */
    private MinioClient createMinioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

    /**
     * 设置存储桶为公开读
     */
    private void setBucketPolicy(MinioClient minioClient) {
        try {
            // 定义桶策略 - 允许公开读取
            String bucketPolicy = "{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": {\"AWS\": [\"*\"]},\n" +
                    "            \"Action\": [\"s3:GetObject\"],\n" +
                    "            \"Resource\": [\"arn:aws:s3:::" + minioConfig.getBucketName() + "/*\"]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            // 设置桶策略
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .config(bucketPolicy)
                            .build()
            );

            log.info("设置存储桶{}为公开读取", minioConfig.getBucketName());
        } catch (Exception e) {
            log.error("设置存储桶策略失败: {}", e.getMessage(), e);
        }
    }
}