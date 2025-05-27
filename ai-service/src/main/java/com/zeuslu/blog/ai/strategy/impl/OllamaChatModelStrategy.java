package com.zeuslu.blog.ai.strategy.impl;

import com.zeuslu.blog.ai.constant.ChatModelConstant;
import com.zeuslu.blog.ai.strategy.ChatModelStrategy;
import com.zeuslu.blog.api.ai.domain.biz.AiMessageContent;
import com.zeuslu.blog.api.ai.domain.dto.AiMessageDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author lumingfan
 */
public class OllamaChatModelStrategy implements ChatModelStrategy {
    private final ChatClient client;
    public OllamaChatModelStrategy(OllamaChatModel ollamaChatModel, ChatMemoryRepository repository, int maxMessages) {
        ChatMemory memory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(maxMessages)
                .build();

        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build());
        this.client = builder.build();
    }

    @Override
    public List<String> getTypes() {
        return List.of(ChatModelConstant.MODEL_TYPE_OLLAMA);
    }

    @Override
    public String call(AiMessageDTO aiMessageDTO) {
        return prompt(aiMessageDTO).call().content();
    }

    @Override
    public Flux<String> stream(AiMessageDTO aiMessageDTO) {
        return prompt(aiMessageDTO).stream().content();
    }

    private ChatClient.ChatClientRequestSpec prompt(AiMessageDTO aiMessageDTO) {
        AiMessageContent content = aiMessageDTO.getContent();
        ChatClient.ChatClientRequestSpec prompt = client.prompt();
        // TODO: 根据消息类型设置
        prompt = prompt.user(content.getContent());
        // TODO: 提供更多的模型选择
        prompt = prompt.options(
                OllamaOptions.builder().model("deepseek-r1:7b").build()
        );
        prompt = prompt.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, aiMessageDTO.getConversationId()));
        return prompt;
    }
}
