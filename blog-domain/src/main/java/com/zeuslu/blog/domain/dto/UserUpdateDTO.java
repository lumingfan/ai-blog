package com.zeuslu.blog.domain.dto;

import com.zeuslu.blog.domain.constant.DtoParamConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息更新数据传输对象
 * @author lumingfan
 */
@Data
@Schema(description = "用户信息更新请求对象")
public class UserUpdateDTO {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    @Size(min = 2, max = 20, message = "昵称长度需在2-20个字符之间")
    private String nickname;

    /**
     * 用户头像文件
     */
    @Schema(description = "用户头像文件", type = "string", format = "binary")
    private MultipartFile avatar;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    @Pattern(regexp = DtoParamConstant.PHONE_REGEX_PATTERN, message = "手机号格式不正确")
    private String phone;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
}