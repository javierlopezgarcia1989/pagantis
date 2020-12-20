package com.pagantis.pagacoin.controller;

import com.pagantis.pagacoin.model.User;
import com.pagantis.pagacoin.model.Wallet;
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
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * getAllWalletsByUser
     * @param userId
     * @return <List<Wallet>> Lista carteras de un usuario
     */
    @GetMapping("/getAllWalletsByUser/{userId}")
    public ResponseEntity<List<Wallet>> getAllWalletsByUser(@PathVariable("userId") String userId) {
        try {
            List<Wallet> wallets = new ArrayList<Wallet>();

            if (userId == null)
                walletRepository.findAll().forEach(wallets::add);
            else
                walletRepository.findByUserId(userId).forEach(wallets::add);

            if (wallets.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(wallets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * getAllWalletsByEmail
     * @param email
     * @return <List<Wallet>> Lista de carteras
     */
    @GetMapping("/getAllWalletsByEmail/{email}")
    public ResponseEntity<List<Wallet>> getAllWalletsByEmail(@PathVariable("email") String email) {
        try {
            List<Wallet> wallets = new ArrayList<Wallet>();
            Optional<User> userData = userRepository.findByEmail(email);

            if (userData.isPresent()) {
                User user = userData.get();
                walletRepository.findByUserId(user.getId()).forEach(wallets::add);

                if (wallets.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(wallets, HttpStatus.OK);

            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * createWallet
     * @param wallet
     * @return Wallet creado
     */
    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        try {
            Wallet _wallet = walletRepository.save(new Wallet(wallet.getUserId(), wallet.getName(), wallet.getMoney()));
            return new ResponseEntity<>(_wallet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
