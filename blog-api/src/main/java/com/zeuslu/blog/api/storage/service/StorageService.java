package com.zeuslu.blog.api.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.storage.domain.dto.FileUploadDTO;
import com.zeuslu.blog.api.storage.domain.po.Storage;
import com.zeuslu.blog.api.storage.domain.vo.FileUploadResponseVO;

/**
 * @author lumingfan
 */
public interface StorageService extends IService<Storage> {
    FileUploadResponseVO uploadFile(FileUploadDTO fileUploadDTO);
}
