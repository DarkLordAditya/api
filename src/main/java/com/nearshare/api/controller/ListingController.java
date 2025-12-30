package com.nearshare.api.controller;

import com.nearshare.api.entity.Listing;
import com.nearshare.api.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    // 1. Add Listing (Fixed for Image Upload + Data)
    // Note: We use @RequestParam instead of @RequestBody because we are sending a File
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addListing(
            @RequestParam("image") MultipartFile image,
            @RequestParam("providerId") Long providerId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("condition") String condition,
            @RequestParam("price") double price,
            @RequestParam("deposit") double deposit,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("pincode") String pincode
    ) {
        try {
            Listing listing = new Listing();
            listing.setProviderId(providerId);
            listing.setTitle(title);
            listing.setDescription(description);
            listing.setCategory(category);
            listing.setItemCondition(condition);
            listing.setPrice(price);
            listing.setDeposit(deposit);
            listing.setAddress(address);
            listing.setCity(city);
            listing.setPincode(pincode);

            // Convert file to bytes and save in DB
            if (!image.isEmpty()) {
                listing.setImageData(image.getBytes());
            }

            listingRepository.save(listing);
            return ResponseEntity.ok("Listing created successfully!");

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving image: " + e.getMessage());
        }
    }

    // 2. Get All Listings
    @GetMapping
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    // 3. Search by City
    @GetMapping("/search")
    public List<Listing> searchByCity(@RequestParam String city) {
        return listingRepository.findByCity(city);
    }

    // 4. Get Listings by Provider (My Listings)
    @GetMapping("/provider/{providerId}")
    public List<Listing> getListingsByProvider(@PathVariable Long providerId) {
        return listingRepository.findByProviderId(providerId);
    }

    // 5. Get Listings by Pincode Range (+/- 2)
    @GetMapping("/pincode/{pincode}")
    public List<Listing> getListingsByPincode(@PathVariable String pincode) {
        try {
            int pin = Integer.parseInt(pincode);
            String minPin = String.valueOf(pin - 2);
            String maxPin = String.valueOf(pin + 2);
            return listingRepository.findByPincodeBetween(minPin, maxPin);
        } catch (NumberFormatException e) {
            return listingRepository.findByPincode(pincode);
        }
    }
}