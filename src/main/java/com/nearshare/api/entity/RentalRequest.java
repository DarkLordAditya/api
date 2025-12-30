package com.nearshare.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_requests")
public class RentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Item Details
    private Long itemId;
    private String itemTitle;

    // Borrower Details
    private Long borrowerId;
    private String borrowerName;

    // Lender Details
    private Long lenderId;
    private String lenderName;
    private String lenderPhone;

    // Request Status
    private String status;

    // --- PAYMENT FIELDS ---
    private String paymentStatus = "UNPAID";
    private String razorpayOrderId;
    private String transactionId;
    private Double amount;

    // --- LOCATION FIELDS ---
    private Double meetingLat;
    private Double meetingLng;

    // ✅ NEW: Secret Token for QR Code
    private String handoverToken;

    private LocalDateTime requestDate;

    public RentalRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // --- GETTERS & SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemTitle() { return itemTitle; }
    public void setItemTitle(String itemTitle) { this.itemTitle = itemTitle; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public Long getLenderId() { return lenderId; }
    public void setLenderId(Long lenderId) { this.lenderId = lenderId; }

    public String getLenderName() { return lenderName; }
    public void setLenderName(String lenderName) { this.lenderName = lenderName; }

    public String getLenderPhone() { return lenderPhone; }
    public void setLenderPhone(String lenderPhone) { this.lenderPhone = lenderPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Double getMeetingLat() { return meetingLat; }
    public void setMeetingLat(Double meetingLat) { this.meetingLat = meetingLat; }

    public Double getMeetingLng() { return meetingLng; }
    public void setMeetingLng(Double meetingLng) { this.meetingLng = meetingLng; }

    public String getHandoverToken() { return handoverToken; }
    public void setHandoverToken(String handoverToken) { this.handoverToken = handoverToken; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
}