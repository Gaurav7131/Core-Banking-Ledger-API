package com.banking.bank_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecureTransferAgentService {

    private static final Logger log = LoggerFactory.getLogger(SecureTransferAgentService.class);

    private final Map<String, Double> accounts = new ConcurrentHashMap<>();
    private final Map<String, PendingApproval> pendingApprovals = new ConcurrentHashMap<>();

    public SecureTransferAgentService() {
        accounts.put("ACC-101", 10000.00);
        accounts.put("ACC-202", 500.00);
    }

    @Tool(description = "Initiates a fund transfer. Transfers over $500 require mandatory human approval.")
    public String requestTransfer(
            @ToolParam(description = "Source account ID") String fromAccount,
            @ToolParam(description = "Destination account ID") String toAccount,
            @ToolParam(description = "Amount in USD") double amount) {

        log.info(" Evaluating transfer request: ${} from {} to {}", amount, fromAccount, toAccount);

        if (!accounts.containsKey(fromAccount) || !accounts.containsKey(toAccount)) {
            return "Transfer failed";
        }

        // HITL(Human in the loop) Threshold Check
        if (amount > 500.00) {
            String approvalId = "REQ-" + System.currentTimeMillis();
            pendingApprovals.put(approvalId, new PendingApproval(fromAccount, toAccount, amount));

            log.warn("[HITL HALT] Transaction exceeds $500 threshold! Halted for human approval", approvalId);
            return String.format("HALTED: Transfer of ${amount} requires supervisor approval. ", amount, approvalId);
        }

        // Auto-approve small transactions(<500.00)
        executeTransferLogic(fromAccount, toAccount, amount);
        return String.format("Successfully auto-transferred ${amount} from %s to %s.", amount, fromAccount, toAccount);
    }

    public String approveAndExecute(String approvalId) {
        PendingApproval approval = pendingApprovals.remove(approvalId);

        if (approval == null) {
            return "Approval failed: Invalid or expired ticket ID";
        }

        executeTransferLogic(approval.from(), approval.to(), approval.amount());
        return String.format("Supervisor approved! Transferred ${amount} from %s to %s.", approval.amount(),
                approval.from(), approval.to());
    }

    private void executeTransferLogic(String from, String to, double amount) {
        accounts.put(from, accounts.get(from) - amount);// deposit
        accounts.put(to, accounts.get(to) + amount);// credited
    }

    private record PendingApproval(String from, String to, double amount) {
    }
}