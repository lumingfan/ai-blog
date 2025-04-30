package com.zeuslu.blog.storage.service.impl;

import cn.hutool.core.util.HashUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.common.errorcode.StorageErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.domain.dto.FileUploadDTO;
import com.zeuslu.blog.domain.po.Storage;
import com.zeuslu.blog.domain.vo.FileUploadResponseVO;
import com.zeuslu.blog.storage.factory.StorageFactory;
import com.zeuslu.blog.storage.mapper.StorageMapper;
import com.zeuslu.blog.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {
    private final StorageFactory storageFactory;
    @Override
    public FileUploadResponseVO uploadFile(FileUploadDTO fileUploadDTO) {
        // 1. 查询数据库是否已经上传内容相同的文件
        String hash = null;
        try {
            hash = Long.toString(HashUtil.murmur64(fileUploadDTO.getFile().getBytes()));
        } catch (IOException e) {
            throw new CommonException(StorageErrorCode.FILE_GET_BYTES_FAILED);
        }
        Storage storage = this.lambdaQuery().eq(Storage::getUniqueId, hash).one();

        if (storage != null) {
            return FileUploadResponseVO.builder()
                    .url(storage.getUrl())
                    .size(fileUploadDTO.getFile().getSize())
                    .build();
        }
        // 2. 上传文件
        FileUploadResponseVO fileUploadResponseVO = storageFactory.getStorageService().uploadFile(fileUploadDTO);
        // 3. 将文件信息存入数据库
        try {
            this.save(Storage.builder()
                    .url(fileUploadResponseVO.getUrl())
                    .uniqueId(hash)
                    .build());
        } catch (DuplicateKeyException ignored) {
            return fileUploadResponseVO;
        }
        return fileUploadResponseVO;
    }
}
