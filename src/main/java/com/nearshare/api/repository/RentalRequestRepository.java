package com.nearshare.api.repository;

import com.nearshare.api.entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Good practice to add this annotation

import java.util.List;

@Repository
public interface RentalRequestRepository extends JpaRepository<RentalRequest, Long> {
    // Find requests sent TO me (as a lender)
    List<RentalRequest> findByLenderId(Long lenderId);

    // Find requests sent BY me (as a borrower)
    List<RentalRequest> findByBorrowerId(Long borrowerId);

    // ✅ NEW: Find request by the secret QR token (Required for Scanner)
    RentalRequest findByHandoverToken(String handoverToken);
}