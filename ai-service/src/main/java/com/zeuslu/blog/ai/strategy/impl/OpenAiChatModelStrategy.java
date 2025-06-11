package com.zeuslu.blog.ai.strategy.impl;

import com.zeuslu.blog.ai.strategy.ChatModelStrategy;
import com.zeuslu.blog.api.ai.domain.biz.AiMessageContent;
import com.zeuslu.blog.api.ai.domain.dto.AiMessageDTO;
import com.zeuslu.blog.common.enums.AiModelEnums;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author lumingfan
 */
public class OpenAiChatModelStrategy implements ChatModelStrategy {
    private final ChatClient client;
    public OpenAiChatModelStrategy(OpenAiChatModel openAiChatModel, ChatMemoryRepository repository, int maxMessages) {
        ChatMemory memory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(maxMessages)
                .build();

        ChatClient.Builder builder = ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build());
        this.client = builder.build();
    }

    @Override
    public List<AiModelEnums> getTypes() {
        return List.of(
                AiModelEnums.GPT_3_5_TURBO,
                AiModelEnums.GPT_4
        );
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
        prompt = prompt.options(
                OpenAiChatOptions.builder().model(aiMessageDTO.getModel().getModel()).build()
        );
        prompt = prompt.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, aiMessageDTO.getConversationId()));
        return prompt;
    }
}
