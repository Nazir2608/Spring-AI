package com.spring.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class ChatService {

    private final ChatClient chatClient;

    @Value("classpath:prompts/system-message.st")
    private Resource systemMessage;

    @Value("classpath:prompts/user-message.st")
    private Resource userMessage;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chatTemplate(String query, String conversationId) {
        return this.chatClient
                .prompt()
                .advisors(a->a.param(CONVERSATION_ID, conversationId))
                .system(system-> system.text(this.systemMessage))
                .user(user->user.text(this.userMessage)
                        .param("concept",query))
                .call()
                .content();

    }

    public Flux<String> streamChat(String q, String conversationId) {
        return  this.chatClient
                .prompt()
                .advisors(a->a.param(CONVERSATION_ID, conversationId))
                .system(system-> system.text(this.systemMessage))
                .user(user->user.text(this.userMessage)
                        .param("concept",q))
                .stream()
                .content();

    }
}
