package com.spring.ai.advisor;

import com.spring.ai.advisor.advisor.TokenPrintAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(new TokenPrintAdvisor(),new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("game", "politics", "violence")))
                .defaultSystem("you are helpful coding assistant..")
                .defaultOptions(OllamaChatOptions
                        .builder()
                        .model("llava:latest")
                        .temperature(0.3)
                        .maxTokens(500)).build();
    }
}
