package com.project.Ewallet.wallet.service.resources;


import com.project.Ewallet.wallet.domain.Wallet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponse {

    private Long userId;
    private Long walletId;
    private Double balance;

    public WalletResponse(Wallet wallet){
        this.walletId = wallet.getId();
        this.balance = wallet.getBalance();
        this.userId = wallet.getUserId();
    }
}
