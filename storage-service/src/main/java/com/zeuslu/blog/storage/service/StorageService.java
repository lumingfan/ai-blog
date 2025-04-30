package com.zeuslu.blog.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.dto.FileUploadDTO;
import com.zeuslu.blog.domain.po.Storage;
import com.zeuslu.blog.domain.vo.FileUploadResponseVO;

/**
 * @author lumingfan
 */
public interface StorageService extends IService<Storage> {
    FileUploadResponseVO uploadFile(FileUploadDTO fileUploadDTO);
}
