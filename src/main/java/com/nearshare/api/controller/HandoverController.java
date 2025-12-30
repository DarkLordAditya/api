package com.nearshare.api.controller;

import com.nearshare.api.entity.RentalRequest;
import com.nearshare.api.repository.RentalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/handover")
@CrossOrigin(origins = "http://localhost:5173")
public class HandoverController {

    @Autowired
    private RentalRequestRepository requestRepository;

    @PostMapping("/verify-qr")
    public ResponseEntity<?> verifyHandover(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");

        // Find request by the unique token
        RentalRequest request = requestRepository.findByHandoverToken(token);

        if (request == null) {
            return ResponseEntity.badRequest().body("Invalid QR Code");
        }

        if ("DELIVERED".equals(request.getStatus())) {
            return ResponseEntity.badRequest().body("Item already delivered!");
        }

        // Update Status
        request.setStatus("DELIVERED");
        requestRepository.save(request);

        return ResponseEntity.ok(Map.of(
                "message", "Handover Successful! Item marked as Delivered.",
                "borrowerName", request.getBorrowerName()
        ));
    }
}