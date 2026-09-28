<div align="center">

# ⚡ Core Banking Ledger API & Autonomous Agent 🏦

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring AI](https://img.shields.io/badge/Spring_AI-1.0.0--M1-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-ai)
[![Groq](https://img.shields.io/badge/Groq-Llama_3_70B-F05032?style=for-the-badge&logo=fastapi&logoColor=white)](https://groq.com/)
[![Database](https://img.shields.io/badge/Database-H2_In--Memory-4479A1?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.h2database.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

<br/>

<!-- Animated Typing Header -->
<a href="https://git.io/typing-svg">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=24&pause=1000&color=22C55E&center=true&vCenter=true&width=650&lines=Autonomous+Agentic+FinTech+Microservice;Zero-Loss+Transactional+Fund+Transfers;Sub-Second+Inference+via+Groq+%2B+Llama+3;In-Memory+RAG+Policy+Retrieval" alt="Typing SVG" />
</a>

<p align="center">
  A high-performance core retail banking backend coupled with an <b>autonomous Agentic AI assistant</b> that reasons, queries real ledger databases, searches internal policies via RAG, and streams answers token-by-token.
</p>

---

<!-- Animated Visual / Wave Divider -->
<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=22C55E,16A34A,0F172A&height=120&section=header" width="100%"/>

</div>

## 💡 Overview

This project unites **enterprise financial transactions** with an **autonomous decision-making AI agent**:

- **Transactional Ledger Engine:** Delivers ACID-compliant money transfers backed by Spring's `@Transactional` boundary, guaranteeing rollback safety with 0% data loss on transfer interruptions.
- **Agentic ReAct AI Layer:** Combines Groq’s ultra-low-latency Llama 3 70B inference engine with Spring AI tools, dynamic function calling, conversational memory, and in-memory policy RAG retrieval.

---

## 🚀 Key Highlights & Architecture

### 🧠 Agentic AI Features

- **Dynamic Tool Calling:** The AI autonomously triggers Spring backend functions (`checkBalance`, `transferMoneyTool`) based on intent.
- **Deterministic Record Bindings:** Typed Java records enforce deterministic, schema-validated JSON payload inputs directly from the LLM.
- **RAG Policy Engine:** Custom in-memory Vector Store enables keyword and similarity lookups for institutional rules (e.g., overdraft fees, daily transfer thresholds).
- **Reactive Streaming:** Leverages Spring WebFlux (`Flux<String>`) for real-time, token-by-token response streams over Server-Sent Events (`text/event-stream`).
- **Conversational Memory:** Stateful multi-turn dialogs via `MessageChatMemoryAdvisor`.

### 💳 Core Banking Operations

- **Full CRUD Retail Accounts:** Safe creation, retrieval, balance adjustment, and account closures.
- **Zero-Loss Transfers:** Atomic execution between source and target accounts with rollback protection.
- **Zero-Config In-Memory DB:** Uses H2 for rapid prototyping and local execution out of the box.

---

## 🛠️ Tech Stack Matrix

| Layer               | Technology                  | Details                                           |
| ------------------- | --------------------------- | ------------------------------------------------- |
| **Language**        | Java 21                     | Modern LTS with Record types                      |
| **Framework**       | Spring Boot 3.3.7           | Enterprise backend runtime                        |
| **AI Architecture** | Spring AI 1.0.0-M1          | ChatClient fluent API, Function Calling, Advisors |
| **LLM Provider**    | Groq Cloud                  | `llama3-70b-8192` high-speed inference            |
| **Data & ORM**      | Spring Data JPA / Hibernate | Repository abstraction layer                      |
| **Database**        | H2 Database                 | Embedded in-memory SQL store                      |
| **Reactive Web**    | Spring WebFlux / SSE        | Streaming token transport                         |

---

## 🚦 Getting Started

### 1. Prerequisites

- **Java 21** installed
- **Maven 3.8+** installed
- A valid **Groq API Key** (from [console.groq.com](https://console.groq.com))

### 2. Configure Environment Variable

```bash
# macOS / Linux
export GROQ_API_KEY="your_groq_api_key_here"

# Windows (Command Prompt)
set GROQ_API_KEY=your_groq_api_key_here

# Windows (PowerShell)
$env:GROQ_API_KEY="your_groq_api_key_here"
```
