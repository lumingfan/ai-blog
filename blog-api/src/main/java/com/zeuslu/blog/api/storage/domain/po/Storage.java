package com.zeuslu.blog.api.storage.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 */
@TableName("tb_storage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    @TableId
    private Long id;
    private String uniqueId;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
