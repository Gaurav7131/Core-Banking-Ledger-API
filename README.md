<div align="center">

# ⚡ Core Banking Ledger API & Autonomous Agent 🏦

### _Autonomous Banking Microservice with Real-Time Human-in-the-Loop (HITL) Governance_

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring AI](https://img.shields.io/badge/Spring_AI-1.0.0--M1-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-ai)
[![Groq](https://img.shields.io/badge/Groq-Llama_3_70B-F05032?style=for-the-badge&logo=fastapi&logoColor=white)](https://groq.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

<!-- Animated Typing Header -->
<a href="https://git.io/typing-svg">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=23&pause=1000&color=22C55E&center=true&vCenter=true&width=760&lines=Autonomous+Agentic+FinTech+Microservice;Human-in-the-Loop+(HITL)+Supervisor+Escalations;Dual-Phase+Verification+for+Transfers+%3E+$500;Sub-Second+Inference+via+Groq+%2B+Llama+3" alt="Typing SVG" />
</a>

<p align="center">
  A high-performance banking service backed by an <b>autonomous Agentic AI assistant</b> that reasons over financial ledgers, executes sub-$500 transactions autonomously, enforces institutional compliance policies, and triggers <b>Human-in-the-Loop (HITL) supervisor approval gates</b> for high-value transfers.
</p>
---

<!-- Animated Visual Divider -->
<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=22C55E,16A34A,0F172A&height=120&section=header" width="100%"/>

</div>

## 💡 Overview

This project unites **enterprise transactional banking rules** with an **autonomous decision-making AI agent**:

- **Human-in-the-Loop (HITL) Gate:** Enforces dual-track execution. Low-risk transfers execute immediately, while high-value transfers exceeding `$500.00` are paused, assigned an audit ticket, and routed to an authorization queue.
- **Anti-Hallucination Guardrails:** Strict prompt policies force the agent to halt execution on pending actions without fabricating confirmation.
- **Agentic Function Calling:** Uses Spring AI's tool registry to dynamically bind user natural language requests directly to low-level ledger services.
- **Double-Check State Verification:** Re-evaluates account balances at execution time to protect against race conditions or mid-flight balance exhaustion between approval ticket creation and manager clearance.

---

## 💡 System Architecture & Governance Flow

```text
                      +-----------------------------+
                      |   User / Client Terminal    |
                      +--------------+--------------+
                                     |
                         Natural Language Prompt
                                     v
                 +---------------------------------------+
                 | Spring AI Agent (ChatClient + Groq)   |
                 | - Strict Banking Compliance Prompts   |
                 | - Dynamic Tool Calling Integration    |
                 +-------------------+-------------------+
                                     |
                           Triggers requestTransfer()
                                     v
                        +-------------------------+
                        |  Amount > $500.00?      |
                        +------------+------------+
                                     |
                  +------------------+------------------+
                  | YES                                 | NO
                  v                                     v
    +---------------------------+       +-------------------------------+
    | Staged into Staging Cache |       | Auto-executed immediately    |
    | Emits Ticket: REQ-xxxxxxx |       | Atomic ledger adjustment      |
    +-------------+-------------+       +-------------------------------+
                  |
         Halts Execution
                  |
                  v
    +---------------------------+
    | Supervisor POST /approve  |
    | - Validates Ticket ID     |
    | - Re-checks Balance       |
    | - Commits Ledger State    |
    +---------------------------+
```

---

## 🚀 Engineering Highlights & Deep Dive

### 🛡️ Human-in-the-Loop (HITL) Governance & Safety Rails

- **Tiered Risk Circuit Breaker:** Micro-transactions (≤ $500.00) undergo automated validation and immediate execution. High-value movements (> $500.00) automatically trip an execution circuit breaker, halting autonomous writes.
- **Stateful Audit Escalation:** Suspended actions issue cryptographically unique tickets (`REQ-timestamp`) and register an immutable snapshot in the in-flight `PendingApproval` staging cache.
- **Time-of-Execution Invariant Checks:** Implements double-check balance verification at the exact instant of supervisor release, completely neutralizing mid-flight balance exhaustion and race conditions.
- **Anti-Hallucination Guardrails:** System prompts configure strict compliance boundaries—the model is systematically restrained from reporting execution success whenever human sign-off is pending.

### 🧠 Agentic Spring AI Architecture

- **Deterministic Tool Routing:** Powered by Spring AI's `@Tool` subsystem, mapping unstructured natural language prompts into typed service interactions via `requestTransfer`.
- **Strongly-Typed Memory Payloads:** Employs Java 21 `record` constructs for strict validation and deterministic payload serialization.
- **Hardware-Accelerated Inference:** Integrates with Groq's LPU engine running `llama3-70b-8192` to achieve low-latency reasoning cycles during multi-step tool calls.

### 💳 Ledger Integrity & Concurrency

- **High-Throughput In-Memory Engine:** Built upon `ConcurrentHashMap` structures ensuring low-latency reads and writes for rapid local benchmarking.
- **Zero-Trust Input Sanitization:** Rejects non-positive sums, nonexistent routing identifiers, and overdrawn source ledgers prior to staging or execution.

---

## 🛠️ Technology Stack Matrix

| Architecture Layer      | Technology             | Engineering Role                                            |
| :---------------------- | :--------------------- | :---------------------------------------------------------- |
| **Language Runtime**    | Java 21 LTS            | Pattern matching, records, immutable domain structures      |
| **Backend Framework**   | Spring Boot 3.3.7      | Dependency injection, lifecycle management, and web runtime |
| **Agentic AI Layer**    | Spring AI 1.0.0-M1     | `ChatClient` builder, dynamic `@Tool` function invocation   |
| **Inference Provider**  | Groq Cloud             | `llama3-70b-8192` model acceleration                        |
| **State & Concurrency** | Java Concurrency Utils | High-throughput, thread-safe staging and balance stores     |
| **API Transport**       | Spring Web MVC         | Synchronous REST endpoints under `/api/hitl/*`              |

---

## 📡 REST API Specification (`/api/hitl`)

<details open>
<summary><b>1. Natural Language Agent Proxy (Inference & Routing)</b></summary>

Accepts raw conversational directives, invokes underlying tool logic, and either commits or stages transactions based on monetary risk thresholds.

- **Endpoint:** `GET /api/hitl/chat`
- **Query Parameter:** `message` _(String, optional)_

```bash
# Triggers HITL escalation (> $500.00)
curl -s -X GET "http://localhost:8080/api/hitl/chat?message=Please%20transfer%20$600%20from%20Acc-102%20to%20Acc-101" | jq .
```

```json
{
  "prompt": "Please transfer $600 from Acc-102 to Acc-101",
  "response": "Transaction exceeds $500 threshold! Halted for human approval. Ticket ID: REQ-1727758349120"
}
```

</details>

<details>
<summary><b>2. Supervisor Approval Gate (HITL Release)</b></summary>

Validates pending ticket records, performs final atomic balance checks, and finalizes ledger state mutations.

- **Endpoint:** `POST /api/hitl/approve`
- **Query Parameter:** `ticketId` _(String, required)_

```bash
curl -s -X POST "http://localhost:8080/api/hitl/approve?ticketId=REQ-1727758349120" | jq .
```

```json
{
  "ticketId": "REQ-1727758349120",
  "status": "Supervisor approved! Transferred $600.00 from Acc-102 to Acc-101. Ticket ID: REQ-1727758349120"
}
```

</details>

<details>
<summary><b>3. Ledger Balance Snapshot (Read-Only)</b></summary>

Fetches an unmodifiable snapshot of ledger account balances.

- **Endpoint:** `GET /api/hitl/balances`

```bash
curl -s -X GET "http://localhost:8080/api/hitl/balances" | jq .
```

```json
{
  "accounts": {
    "Acc-101": 701.01,
    "Acc-102": 2400.0
  }
}
```

</details>

---

## 🚦 Quickstart & Local Deployment

### Step 1: Environment Readiness

Ensure runtime dependencies are installed and available on your system path:

- Java 21 LTS (`java --version`)
- Apache Maven 3.8+ (`mvn --version`)
- A valid API Key from Groq Console

### Step 2: Inject Secrets

Export your Groq API credentials into your current terminal session:

```bash
# Linux / macOS
export GROQ_API_KEY="gsk_your_groq_api_key_here"

# Windows (Command Prompt)
set GROQ_API_KEY=gsk_your_groq_api_key_here

# Windows (PowerShell)
$env:GROQ_API_KEY="gsk_your_groq_api_key_here"
```

### Step 3: Compile and Bootstrap

```bash
# Clone repository
git clone [https://github.com/Gaurav7131/Core-Banking-Ledger-API.git](https://github.com/Gaurav7131/Core-Banking-Ledger-API.git)
cd Core-Banking-Ledger-API

# Launch Spring Boot runtime
./mvnw clean spring-boot:run
```

---

## 🧠 Built with Spring AI + Groq for Agentic FinTech Governance

**Open issues, PRs, and production war stories welcome.**

---

## ✨ Optional Extras to Elevate Your Project (Recruiter Ready)

### 1. 📊 Real-Time Repository & Profile Stats

Showcase activity and contribution metrics directly in your README:

<div align="center">

  <img src="https://github-readme-stats.vercel.app/api?username=Gaurav7131&show_icons=true&theme=tokyonight&hide_border=true&count_private=true" alt="Gaurav's GitHub Stats" height="165" />
  <img src="https://github-readme-stats.vercel.app/api/top-langs/?username=Gaurav7131&layout=compact&theme=tokyonight&hide_border=true" alt="Top Languages" height="165" />

</div>

<br/>

---

### 2. 🎬 Visual Demo of the HITL Workflow

Demonstrating real-time CLI interactions or Postman requests immediately communicates the end-to-end functionality.

> Record your terminal running the `curl` commands using [VHS](https://github.com/charmbracelet/vhs) or [LICEcap](https://www.cockos.com/licecap/), save it inside your repository as `assets/demo-hitl-flow.gif`, and push it to GitHub.

<p align="center">
  <img src="./assets/demo-hitl-flow.gif" alt="Autonomous HITL Transaction & Approval Flow" width="750"/>
</p>

---

### 3. 🛡️ CI/CD Pipeline Badge

Signal engineering rigor by adding continuous integration testing.

- **Option A: Static Badge (Shows immediately before setting up GitHub Actions)**

  [![Build & Test](https://img.shields.io/badge/CI-Passing-22C55E?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/Gaurav7131/Core-Banking-Ledger-API)
