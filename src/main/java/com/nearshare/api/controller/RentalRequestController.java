package com.nearshare.api.controller;

import com.nearshare.api.entity.RentalRequest;
import com.nearshare.api.repository.RentalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RentalRequestController {

    @Autowired
    private RentalRequestRepository requestRepository;

    // 1. Create a Request (Borrower clicks "Rent Now")
    @PostMapping
    public ResponseEntity<RentalRequest> createRequest(@RequestBody RentalRequest request) {
        request.setStatus("PENDING");
        return ResponseEntity.ok(requestRepository.save(request));
    }

    // 2. Get My Incoming Requests (For the Item Owner)
    @GetMapping("/lender/{lenderId}")
    public List<RentalRequest> getLenderRequests(@PathVariable Long lenderId) {
        return requestRepository.findByLenderId(lenderId);
    }

    // 3. Update Status (Accept/Reject)
    @PutMapping("/{id}/status")
    public ResponseEntity<RentalRequest> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return requestRepository.findById(id).map(request -> {
            request.setStatus(status);
            return ResponseEntity.ok(requestRepository.save(request));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ 4. NEW: Get My Outgoing Requests (For the Borrower/Renter)
    // Endpoint: GET /api/requests/borrower/{borrowerId}
    @GetMapping("/borrower/{borrowerId}")
    public List<RentalRequest> getBorrowerRequests(@PathVariable Long borrowerId) {
        return requestRepository.findByBorrowerId(borrowerId);
    }
}