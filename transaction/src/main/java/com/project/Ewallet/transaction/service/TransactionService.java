package com.project.Ewallet.transaction.service;

import com.project.Ewallet.transaction.service.resources.TransactionRequest;

public interface TransactionService {

    public boolean performTransaction(Long userId, TransactionRequest transactionRequest);
}
