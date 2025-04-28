package com.zeuslu.blog.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author lumingfan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页查询条件")
@Accessors(chain = true)
public class PageQuery {
    @Schema(description = "页码")
    @Min(value = 1, message = "页码不能小于1")
    private Integer page;
    @Schema(description = "页码")
    @Min(value = 1, message = "每页查询数量不能小于1")
    private Integer pageSize;

    /**
     * @return MybatisPlus的Page类
     */
    public <T> Page<T> toPage() {
        return new Page<>(page, pageSize);
    }
}
