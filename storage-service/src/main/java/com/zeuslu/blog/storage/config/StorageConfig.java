package com.zeuslu.blog.storage.config;

import com.zeuslu.blog.storage.enums.StorageTypeEnum;
import com.zeuslu.blog.storage.factory.StorageFactory;
import com.zeuslu.blog.storage.strategy.StorageStrategy;
import com.zeuslu.blog.storage.strategy.impl.MinioStrategy;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lumingfan
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageConfig {
    // 默认使用minio
    private StorageTypeEnum type = StorageTypeEnum.MINIO;
    
    private CosConfig cos;
    private MinioConfig minio;
    private OssConfig oss;
    
    @Data
    public static class CosConfig {
        private String secretId;
        private String secretKey;
        private String region;
        private String bucketName;
        private String baseUrl;
    }
    
    @Data
    public static class MinioConfig {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucketName;
        private String baseUrl;
    }
    
    @Data
    public static class OssConfig {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
        private String baseUrl;
    }

    @Bean
    @ConditionalOnMissingBean(StorageFactory.class)
    public StorageFactory storageFactory() {
        List<StorageStrategy> storageStrategies = new ArrayList<>();
        storageStrategies.add(new MinioStrategy(this.getMinio()));
        return new StorageFactory(storageStrategies, this);
    }

}