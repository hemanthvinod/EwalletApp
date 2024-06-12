package com.project.Ewallet.wallet.service.Impl;

import com.project.Ewallet.wallet.domain.TransactionType;
import com.project.Ewallet.wallet.domain.Wallet;
import com.project.Ewallet.wallet.exception.WalletException;
import com.project.Ewallet.wallet.repository.WalletRepository;
import com.project.Ewallet.wallet.service.WalletService;
import com.project.Ewallet.wallet.service.resources.WalletResponse;
import com.project.Ewallet.wallet.service.resources.WalletTransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository walletRepository;

    @Override
    public void createWallet(Long userId) {

        try{
            Optional<Wallet> optionalWallet= walletRepository.findByUserId(userId);
            if(optionalWallet.isPresent()) {
                logger.info("Wallet already exists fo user :{}", userId);
                return;
            }
                Wallet wallet = new Wallet();
                wallet.setUserId(userId);
                wallet.setBalance(0.0);
                walletRepository.save(wallet);
        }catch(Exception e){
            logger.error("Exception while creating wallet : {}", e.getMessage());
        }
    }

    @Override
    public Wallet deleteWallet(Long userId) {
        try{
            Optional<Wallet> optionalWallet= walletRepository.findByUserId(userId);
            if(optionalWallet.isPresent()) {
                Wallet wallet = optionalWallet.get();
                walletRepository.delete(wallet);
                return wallet;
            }else{
                logger.info("Wallet does not exists fo user :{}", userId);
                return null;
            }
        }catch(Exception e){
            logger.error("Exception while deleting wallet : {}", e.getMessage());
            return null;
        }
    }

    @Override
    public WalletResponse getWallet(Long userId) {
        Optional<Wallet> optionalWallet= walletRepository.findByUserId(userId);
        if(optionalWallet.isPresent()){
            WalletResponse walletResponse = new WalletResponse(optionalWallet.get());
            return walletResponse;
        }else{
            throw new WalletException("EWALLET_DOESNOT_EXIST_EXCEPTION","wallet not found");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = WalletException.class)
    public boolean performTransaction(WalletTransactionRequest walletTransactionRequest) {

        Optional<Wallet> senderWallet = walletRepository.findByUserId(walletTransactionRequest.getSenderId());
        Optional<Wallet> receiverWallet = walletRepository.findByUserId(walletTransactionRequest.getReceiverId());

        if(TransactionType.DEPOSIT.name().equals(walletTransactionRequest.getTransactionType())){
            if(receiverWallet.isEmpty()){
                throw new WalletException("WALLET_NOT_FOUND_EXCEPTION","wallet not found for deposit");
            }
            updateWallet(receiverWallet.get(), walletTransactionRequest.getAmount());
            return true;
        }
        else if(TransactionType.WITHDRAW.name().equals(walletTransactionRequest.getTransactionType())){
            if(receiverWallet.isEmpty()){
                throw new WalletException("WALLET_NOT_FOUND_EXCEPTION","wallet not found for withdrawal");
            }
            updateWallet(receiverWallet.get(), -1 * walletTransactionRequest.getAmount());
            return true;
        }
        else if(TransactionType.TRANSFER.name().equals(walletTransactionRequest.getTransactionType())){
            try{
                    if(senderWallet.isEmpty() || receiverWallet.isEmpty()){
                        throw new WalletException("EWALLET_WALLET_NOT_FOUND","wallet not found exception");
                    }
                    handleTransaction(senderWallet.get(), receiverWallet.get(),walletTransactionRequest.getAmount());
                    return true;
            }catch(WalletException ex){
                logger.error("Exception while performing transaction: {}", ex.getMessage());
                throw ex;
            }
        }
        else{
            throw new WalletException("EWALLET_INVALID-TRANSACTION_TYPE","invalid transaction");
        }
    }

    public void updateWallet(Wallet wallet, Double amount){
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
    }

    public void handleTransaction(Wallet senderWallet, Wallet receiverWallet, Double amount){
        try {
            Wallet senderCopy = new Wallet();
            Wallet receiverCopy = new Wallet();
            BeanUtils.copyProperties(senderWallet, senderCopy);
            BeanUtils.copyProperties(receiverWallet, receiverCopy);
            if (senderWallet.getBalance() < amount) {
                throw new WalletException("INSUFFICIENT_BALANCE_EXCEPTION", "sender has insufficient balance");
            }
            senderCopy.setBalance(senderWallet.getBalance() - amount);
            receiverCopy.setBalance(receiverWallet.getBalance() + amount);
            walletRepository.save(senderCopy);
            walletRepository.save(receiverCopy);
        }
        catch (WalletException ex){
            throw ex;
        }
    }
}
