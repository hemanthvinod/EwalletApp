package com.project.Ewallet.wallet.service;

import com.project.Ewallet.wallet.domain.Wallet;
import com.project.Ewallet.wallet.service.resources.WalletResponse;
import com.project.Ewallet.wallet.service.resources.WalletTransactionRequest;

public interface WalletService {

    public void createWallet(Long userId);

    public Wallet deleteWallet(Long userId);

    public WalletResponse getWallet (Long userId);

    public boolean performTransaction (WalletTransactionRequest walletTransactionRequest);
}
