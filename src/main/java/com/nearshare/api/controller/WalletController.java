package com.nearshare.api.controller;

import com.nearshare.api.entity.User;
import com.nearshare.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:5173")
public class WalletController {

    @Autowired
    private UserRepository userRepository;

    // 1. Get Wallet Balance
    @GetMapping("/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("User not found");
        return ResponseEntity.ok(Map.of("balance", user.getWalletBalance()));
    }

    // 2. Add Money to Wallet
    @PostMapping("/add")
    public ResponseEntity<?> addMoney(@RequestBody Map<String, Object> payload) {
        Long userId = Long.valueOf(payload.get("userId").toString());
        Double amount = Double.valueOf(payload.get("amount").toString());

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        // Update Balance
        user.setWalletBalance(user.getWalletBalance() + amount);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "Money added successfully",
                "newBalance", user.getWalletBalance()
        ));
    }
}