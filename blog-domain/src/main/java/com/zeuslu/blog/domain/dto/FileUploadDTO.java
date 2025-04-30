package com.zeuslu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件上传DTO")
public class FileUploadDTO {
    @Schema(description = "文件类型")
    @NotNull(message = "文件不能为空")
    private MultipartFile file;
    @Schema(description = "文件上传类型")
    private String type;
    @Schema(description = "文件上传大小限制")
    private Long maxSize;
}
