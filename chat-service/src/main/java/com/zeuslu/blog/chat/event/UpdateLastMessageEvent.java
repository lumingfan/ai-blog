package com.zeuslu.blog.chat.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 更新对话表中最后一条消息事件
 * @author lumingfan
 */
@Data
@AllArgsConstructor
public class UpdateLastMessageEvent {
    private Long lastMessageId;
}
