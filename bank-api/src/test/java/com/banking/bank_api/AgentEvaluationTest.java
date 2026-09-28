package com.banking.bank_api;

import org.junit.jupiter.api.Test;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AgentEvaluationTest {

    @Autowired
    private ChatModel chatModel;

    @Test
    void testAgentRelevancy() {
        RelevancyEvaluator evaluator = new RelevancyEvaluator(ChatClient.builder(chatModel));

        String userQuestion = "What is the overdraft fee?";
        String agentResponse = "According to bank policy, the overdraft fee is $35.";

        // Create request without Builder - direct constructor
        EvaluationRequest request = new EvaluationRequest(agentResponse, null, null);

        EvaluationResponse response = evaluator.evaluate(request);

        assertTrue(response.isPass(), "The agent's response was not relevant to the user's prompt!");
    }
}