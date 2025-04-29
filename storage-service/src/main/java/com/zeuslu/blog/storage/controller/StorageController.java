package com.zeuslu.blog.storage.controller;

import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.FileUploadDTO;
import com.zeuslu.blog.domain.vo.FileUploadResponseVO;
import com.zeuslu.blog.storage.factory.StorageFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lumingfan
 */
@Log
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "文件存储接口")
public class StorageController {
    private final StorageFactory storageFactory;

    @PostMapping("/upload")
    public Result<FileUploadResponseVO> uploadFile(@ModelAttribute FileUploadDTO fileUploadDTO) {
        return Result.ok(storageFactory.getStorageService()
                .uploadFile(fileUploadDTO));
    }

}
