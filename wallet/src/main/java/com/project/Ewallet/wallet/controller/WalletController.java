package com.project.Ewallet.wallet.controller;

import com.project.Ewallet.wallet.service.WalletService;
import com.project.Ewallet.wallet.service.resources.WalletResponse;
import com.project.Ewallet.wallet.service.resources.WalletTransactionRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("wallet/{userId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long userId){
        return new ResponseEntity<>(walletService.getWallet(userId), HttpStatus.OK);
    }

    @PostMapping("wallet/transaction")
    public ResponseEntity<Boolean> performTransaction(@RequestBody WalletTransactionRequest walletTransactionRequest){

        Boolean tx = walletService.performTransaction(walletTransactionRequest);
        if(tx) return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }
}
