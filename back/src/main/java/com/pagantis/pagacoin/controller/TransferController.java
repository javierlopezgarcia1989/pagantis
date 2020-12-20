package com.pagantis.pagacoin.controller;

import com.pagantis.pagacoin.model.Transfer;
import com.pagantis.pagacoin.model.User;
import com.pagantis.pagacoin.model.Wallet;
import com.pagantis.pagacoin.repository.TransferRepository;
import com.pagantis.pagacoin.repository.UserRepository;
import com.pagantis.pagacoin.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * makeTransfer
     * @param transfer
     * @return Transferencia hecha
     */
    @PostMapping("/makeTransfer")
    public ResponseEntity<Transfer> makeTransfer(@RequestBody Transfer transfer) {
        try {
            Optional<Wallet> sourcewalletData = walletRepository.findById(transfer.getWalletId());
            Optional<Wallet> destinationWalletData = walletRepository.findById(transfer.getWalletIdDestination());

            if(sourcewalletData.isPresent() && destinationWalletData.isPresent()){
                if(sourcewalletData.get().getMoney() >= transfer.getMoney()) {
                    Double sourceMoney = sourcewalletData.get().getMoney() - transfer.getMoney();
                    Double destinationMoney = destinationWalletData.get().getMoney() + transfer.getMoney();
                    Wallet sourceWallet = sourcewalletData.get();
                    Wallet destinationWallet = destinationWalletData.get();
                    sourceWallet.setMoney(sourceMoney);
                    destinationWallet.setMoney(destinationMoney);
                    walletRepository.save(sourceWallet);
                    walletRepository.save(destinationWallet);

                    Transfer _transfer = transferRepository.save(new Transfer(transfer.getWalletId(), transfer.getWalletIdDestination(), transfer.getMoney()));
                    return new ResponseEntity<>(_transfer, HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
