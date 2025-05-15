package com.zeuslu.blog.ai.config;

import com.zeuslu.blog.ai.strategy.ChatModelStrategy;
import com.zeuslu.blog.ai.strategy.impl.OllamaChatModelStrategy;
import lombok.Data;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.model.ollama.autoconfigure.OllamaConnectionDetails;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置AI模型
 * @author lumingfan
 */
@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class ChatModelConfig {
    private String maxMessages;
    /**
     * 配置策略类
     */
    @Bean
    public List<ChatModelStrategy> chatModels(
            OllamaChatModel ollamaChatModel,
            JdbcChatMemoryRepository repository
    ) {
        List<ChatModelStrategy> chatModelStrategyList = new ArrayList<>();
        chatModelStrategyList.add(new OllamaChatModelStrategy(ollamaChatModel, repository, Integer.parseInt(maxMessages)));
        return chatModelStrategyList;
    }

    /**
     * 配置Ollama模型访问超时时间
     */
    @Bean
    @Qualifier("OllamaRestClientBuilder")
    public RestClient.Builder ollamaRestClientBuilder() {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(HttpClient.newHttpClient());
        requestFactory.setReadTimeout(Duration.ofMinutes(2));
        return RestClient.builder().requestFactory(requestFactory);
    }

    @Bean
    public OllamaApi ollamaApi(OllamaConnectionDetails connectionDetails,
                               @Qualifier("OllamaRestClientBuilder") RestClient.Builder restClientBuilder,
                               ObjectProvider<WebClient.Builder> webClientBuilderProvider) {
        return OllamaApi.builder()
                        .baseUrl(connectionDetails.getBaseUrl())
                .restClientBuilder(restClientBuilder)
                .webClientBuilder(webClientBuilderProvider.getIfAvailable(WebClient::builder)).build();
    }

}
