package com.assignment2.question6_userProfile_api.controller;

import com.assignment2.question6_userProfile_api.model.ApiResponse;
import com.assignment2.question6_userProfile_api.model.UserProfile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final Map<Long, UserProfile> profileMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Create a new profile
    @PostMapping
    public ApiResponse<UserProfile> createProfile(@RequestBody UserProfile profile) {
        if (profile.getUsername() == null || profile.getEmail() == null) {
            return new ApiResponse<>(false, "Username and email are required", null);
        }

        // precise validation
        if (profileMap.values().stream().anyMatch(p -> p.getUsername().equals(profile.getUsername()))) {
            return new ApiResponse<>(false, "Username already exists", null);
        }

        Long id = idGenerator.getAndIncrement();
        profile.setUserId(id);
        profile.setActive(true); // Default to active
        profileMap.put(id, profile);
        return new ApiResponse<>(true, "User profile created successfully", profile);
    }

    // Get profile by ID
    @GetMapping("/{id}")
    public ApiResponse<UserProfile> getProfile(@PathVariable Long id) {
        UserProfile profile = profileMap.get(id);
        if (profile == null) {
            return new ApiResponse<>(false, "User not found", null);
        }
        return new ApiResponse<>(true, "User profile retrieved successfully", profile);
    }

    // Update profile
    @PutMapping("/{id}")
    public ApiResponse<UserProfile> updateProfile(@PathVariable Long id, @RequestBody UserProfile updatedProfile) {
        UserProfile profile = profileMap.get(id);
        if (profile == null) {
            return new ApiResponse<>(false, "User not found", null);
        }

        if (updatedProfile.getUsername() != null)
            profile.setUsername(updatedProfile.getUsername());
        if (updatedProfile.getEmail() != null)
            profile.setEmail(updatedProfile.getEmail());
        if (updatedProfile.getFullName() != null)
            profile.setFullName(updatedProfile.getFullName());
        if (updatedProfile.getAge() != 0)
            profile.setAge(updatedProfile.getAge());
        if (updatedProfile.getCountry() != null)
            profile.setCountry(updatedProfile.getCountry());
        if (updatedProfile.getBio() != null)
            profile.setBio(updatedProfile.getBio());

        return new ApiResponse<>(true, "User profile updated successfully", profile);
    }

    // Delete profile
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProfile(@PathVariable Long id) {
        if (profileMap.remove(id) == null) {
            return new ApiResponse<>(false, "User not found", null);
        }
        return new ApiResponse<>(true, "User profile deleted successfully", null);
    }

    // Search by username, country, or age range
    @GetMapping("/search")
    public ApiResponse<List<UserProfile>> searchProfiles(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {

        List<UserProfile> results = profileMap.values().stream()
                .filter(p -> (username == null || p.getUsername().toLowerCase().contains(username.toLowerCase())))
                .filter(p -> (country == null || p.getCountry().equalsIgnoreCase(country)))
                .filter(p -> (minAge == null || p.getAge() >= minAge))
                .filter(p -> (maxAge == null || p.getAge() <= maxAge))
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "Profiles found", results);
    }

    // Activate profile
    @PostMapping("/{id}/activate")
    public ApiResponse<UserProfile> activateProfile(@PathVariable Long id) {
        UserProfile profile = profileMap.get(id);
        if (profile == null) {
            return new ApiResponse<>(false, "User not found", null);
        }
        profile.setActive(true);
        return new ApiResponse<>(true, "User activated successfully", profile);
    }

    // Deactivate profile
    @PostMapping("/{id}/deactivate")
    public ApiResponse<UserProfile> deactivateProfile(@PathVariable Long id) {
        UserProfile profile = profileMap.get(id);
        if (profile == null) {
            return new ApiResponse<>(false, "User not found", null);
        }
        profile.setActive(false);
        return new ApiResponse<>(true, "User deactivated successfully", profile);
    }
}
