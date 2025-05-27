package com.zeuslu.blog.storage.controller;

import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.api.storage.domain.dto.FileUploadDTO;
import com.zeuslu.blog.api.storage.domain.vo.FileUploadResponseVO;
import com.zeuslu.blog.api.storage.service.StorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class StorageController {
    private final StorageService storageService;

    @PostMapping("/upload")
    public Result<FileUploadResponseVO> uploadFile(@ModelAttribute @Valid FileUploadDTO fileUploadDTO) {
        return Result.ok(storageService.uploadFile(fileUploadDTO));
    }

}
