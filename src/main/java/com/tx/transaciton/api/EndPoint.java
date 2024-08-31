package com.tx.transaciton.api;

import com.tx.transaciton.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndPoint {

    private final TransactionService transactionService;

    public EndPoint(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Integer> transaction() {
        return ResponseEntity.ok(transactionService.transaction());
    }
}
