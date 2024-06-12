package com.project.Ewallet.wallet.repository;

import com.project.Ewallet.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

     Optional<Wallet> findByUserId(Long userId);

}
