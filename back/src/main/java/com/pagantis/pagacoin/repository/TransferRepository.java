package com.pagantis.pagacoin.repository;

import com.pagantis.pagacoin.model.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransferRepository extends MongoRepository<Transfer, String> {
}
