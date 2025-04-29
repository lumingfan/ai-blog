package com.zeuslu.blog.storage.util;

import com.zeuslu.blog.domain.dto.FileUploadDTO;
import com.zeuslu.blog.domain.vo.FileUploadResponseVO;
import com.zeuslu.blog.storage.enums.StorageTypeEnum;

/**
 * @author lumingfan
 */
public interface StorageStrategy {
    /**
     * 上传文件
     * @param fileUploadDTO 文件对象+元数据
     * @return 文件访问URL+文件大小
     */
    FileUploadResponseVO uploadFile(FileUploadDTO fileUploadDTO);

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
