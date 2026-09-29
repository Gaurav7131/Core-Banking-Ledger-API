package com.banking.bank_api.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.bank_api.service.SecureTransferAgentService;

@RestController
@RequestMapping("/api/approval")
public class HitlController {
    private final ChatClient chatClient;
    private final SecureTransferAgentService service;

    // constuctor
    public HitlController(ChatClient.Builder chatBuilder, SecureTransferAgentService service) {
        this.chatClient = chatBuilder
                .defaultSystem(
                        """
                                This is the Secure tranfer agent that Initiates Human in the loop architexture pattern that halt its  execution, ask for approval of human operator before performing high riks operations e.g High amount of transactions,dropping database tables,deploy production code
                                """)
                .defaultTools(service).build();
    }

    @GetMapping("/ask")
    public Map<String, String> agentChat(@RequestParam String message) {
        String response = chatClient.prompt().user(message).call().content();

        return Map.of("prompt", message, "response", response != null ? response : "No response generated");

    }

    @PostMapping("/approve")
    public Map<String, String> approveTransaction(@RequestParam String ticketId) {
        String result = service.approveAndExecute(ticketId);
        return Map.of("ticketId", ticketId,
                "result", result != null ? result : "No result generated");
    }

}
