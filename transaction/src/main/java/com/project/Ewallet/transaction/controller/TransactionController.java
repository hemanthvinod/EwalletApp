package com.project.Ewallet.transaction.controller;

import com.project.Ewallet.transaction.service.TransactionService;
import com.project.Ewallet.transaction.service.resources.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("transaction/{userId}")
    public ResponseEntity<Boolean> transaction(@PathVariable ("userId") Long userId,@RequestBody TransactionRequest transactionRequest){
        System.out.println(transactionRequest);
       Boolean tx = transactionService.performTransaction(userId,transactionRequest);
        if(tx) return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false,HttpStatus.OK);
    }
}
