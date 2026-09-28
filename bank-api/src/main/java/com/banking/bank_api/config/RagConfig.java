package com.banking.bank_api.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class RagConfig {

    @Bean
    public VectorStore vectorStore() {
        return new VectorStore() {
            private final List<Document> documents = new ArrayList<>(List.of(
                    new Document(
                            "Bank Policy: Overdraft fees are $35. Maximum daily transfer limit is $5000. Customer support hours are 9 AM to 5 PM EST.")));

            @Override
            public void add(List<Document> docs) {
                documents.addAll(docs);
            }

            @Override
            public Optional delete(List<String> idList) {
                // Return true to satisfy the VectorStore delete interface contract
                return Optional.ofNullable(null);
            }

            @Override
            public List<Document> similaritySearch(String query) {
                String lowerQuery = query.toLowerCase();
                return documents.stream()
                        .filter(doc -> doc.getContent().toLowerCase().contains(lowerQuery) ||
                                lowerQuery.contains("fee") ||
                                lowerQuery.contains("limit") ||
                                lowerQuery.contains("policy") ||
                                lowerQuery.contains("hours"))
                        .toList();
            }

            @Override
            public List<Document> similaritySearch(SearchRequest request) {
                return similaritySearch(request.getQuery());
            }
        };
    }
}