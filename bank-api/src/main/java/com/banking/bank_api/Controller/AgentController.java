package com.banking.bank_api.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final ChatClient chatClient;

    public AgentController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                // System Prompts (Guardrails)
                .defaultSystem("You are an autonomous FinTech support agent. " +
                        "Use provided tools to check balances or transfer money. " +
                        "Use the provided context to answer policy questions. " +
                        "Do not guess financial data.")
                // Memory Advisor for conversational state
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                // RAG Advisor with VectorStore and default search configuration
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                // Bind the tools
                .defaultFunctions("checkBalance", "transferMoneyTool")
                .build();
    }

    // Streaming Token-by-Token responses via WebFlux
    @GetMapping("/stream")
    public Flux<String> chatStream(@RequestParam String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }
}