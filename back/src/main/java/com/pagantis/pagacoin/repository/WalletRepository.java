package com.pagantis.pagacoin.repository;

import com.pagantis.pagacoin.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    List<Wallet> findByUserId(String userId);
}
