package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件上传响应对象")
public class FileUploadResponseVO {
    @Schema(description = "文件访问地址")
    private String url;
    @Schema(description = "文件大小(bytes)")
    private Long size;
}
