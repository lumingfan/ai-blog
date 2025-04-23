package com.zeuslu.blog.storage.util;

import com.zeuslu.blog.storage.enums.StorageTypeEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lumingfan
 */
public interface StorageStrategy {
    /**
     * 上传文件
     * @param file 文件对象
     * @param dirPrefix 目录前缀
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String dirPrefix);

    /**
     * 删除文件
     * @param fileUrl 文件URL
     * @return 是否删除成功
     */
    boolean deleteFile(String fileUrl);

    /**
     * 获取存储服务类型
     * @return 存储类型
     */
    StorageTypeEnum getType();
}
