package com.zeuslu.blog.notification.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeuslu.blog.api.notification.domain.po.Notification;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author lumingfan
 */
public interface NotificationMapper extends BaseMapper<Notification> {
    @MapKey("type")
    List<Map<String, Object>> countGroupByType(Long userId);
}
