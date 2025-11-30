package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.CompanyFollowerRequestDTO;
import com.alumniportal.unmsm.dto.response.CompanyFollowerResponseDTO;
import com.alumniportal.unmsm.service.interfaces.ICompanyFollowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company-follower")
public class CompanyFollowerRestController {

    private final ICompanyFollowerService companyFollowerService;

    @PostMapping("/follow")
    @Operation(summary = "Follow a company")
    @ApiResponse(responseCode = "201", description = "Company followed successfully")
    public ResponseEntity<CompanyFollowerResponseDTO> followCompany(
            @RequestBody CompanyFollowerRequestDTO companyFollowerRequestDTO) {
        CompanyFollowerResponseDTO response = companyFollowerService.followCompany(companyFollowerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/unfollow")
    @Operation(summary = "Unfollow a company")
    @ApiResponse(responseCode = "204", description = "Company unfollowed successfully")
    public ResponseEntity<Void> unfollowCompany(@RequestBody CompanyFollowerRequestDTO companyFollowerRequestDTO) {
        companyFollowerService.unfollowCompany(companyFollowerRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/following")
    @Operation(summary = "Get all companies followed by user")
    @ApiResponse(responseCode = "200", description = "List of followed companies")
    public ResponseEntity<List<CompanyFollowerResponseDTO>> getFollowedCompaniesByUserId(@PathVariable Long userId) {
        List<CompanyFollowerResponseDTO> response = companyFollowerService.getFollowedCompaniesByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/company/{companyId}/followers")
    @Operation(summary = "Get all followers of a company")
    @ApiResponse(responseCode = "200", description = "List of followers")
    public ResponseEntity<List<CompanyFollowerResponseDTO>> getFollowersByCompanyId(@PathVariable Long companyId) {
        List<CompanyFollowerResponseDTO> response = companyFollowerService.getFollowersByCompanyId(companyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-following")
    @Operation(summary = "Check if user follows company")
    @ApiResponse(responseCode = "200", description = "Returns true if user follows company")
    public ResponseEntity<Map<String, Boolean>> isUserFollowingCompany(@RequestParam Long userId,
                                                                       @RequestParam Long companyId) {
        boolean isFollowing = companyFollowerService.isUserFollowingCompany(userId, companyId);
        return ResponseEntity.ok(Map.of("isFollowing", isFollowing));
    }

    @GetMapping("/company/{companyId}/count")
    @Operation(summary = "Count followers of a company")
    @ApiResponse(responseCode = "200", description = "Number of followers")
    public ResponseEntity<Map<String, Long>> countFollowersByCompanyId(@PathVariable Long companyId) {
        Long count = companyFollowerService.countFollowersByCompanyId(companyId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Count companies followed by user")
    @ApiResponse(responseCode = "200", description = "Number of companies followed")
    public ResponseEntity<Map<String, Long>> countFollowingByUserId(@PathVariable Long userId) {
        Long count = companyFollowerService.countFollowingByUserId(userId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all company followers")
    @ApiResponse(responseCode = "200", description = "List of all company followers")
    public ResponseEntity<List<CompanyFollowerResponseDTO>> findAll() {
        List<CompanyFollowerResponseDTO> response = companyFollowerService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company follower by id")
    @ApiResponse(responseCode = "200", description = "Company follower found")
    public ResponseEntity<CompanyFollowerResponseDTO> findById(@PathVariable Long id) {
        CompanyFollowerResponseDTO response = companyFollowerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company follower by id")
    @ApiResponse(responseCode = "204", description = "Company follower deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        companyFollowerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
