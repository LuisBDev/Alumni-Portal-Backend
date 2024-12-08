package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.response.CompanyResponseDTO;
import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.service.interfaces.ICompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyRestController {

    private final ICompanyService companyService;

    @GetMapping("/all")
    @Operation(summary = "Get all companies")
    @ApiResponse(responseCode = "200", description = "List of companies")
    public ResponseEntity<List<CompanyResponseDTO>> findAll() {
        List<CompanyResponseDTO> companyResponseDTOList = companyService.findAll();
        return ResponseEntity.ok(companyResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by id")
    @ApiResponse(responseCode = "200", description = "Return company found")
    public ResponseEntity<CompanyResponseDTO> findById(@PathVariable Long id) {
        CompanyResponseDTO companyResponseDTO = companyService.findById(id);
        return ResponseEntity.ok(companyResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company by id")
    @ApiResponse(responseCode = "204", description = "Company deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get company by email")
    @ApiResponse(responseCode = "200", description = "Return company found by email")
    public ResponseEntity<CompanyResponseDTO> findByEmail(@PathVariable String email) {
        CompanyResponseDTO companyResponseDTO = companyService.findByEmail(email);
        return ResponseEntity.ok(companyResponseDTO);
    }

    //Actualizar parcialmente los campos
    @PatchMapping("/{id}")
    @Operation(summary = "Update company by id")
    @ApiResponse(responseCode = "200", description = "Return company updated")
    public ResponseEntity<CompanyResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        CompanyResponseDTO companyResponseDTO = companyService.updateCompany(id, updates);
        return ResponseEntity.ok(companyResponseDTO);
    }

    @PostMapping("/updatePassword/{id}")
    @Operation(summary = "Update password by id")
    @ApiResponse(responseCode = "204", description = "Password updated")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        companyService.updatePassword(id, passwordChangeRequestDTO);
        return ResponseEntity.noContent().build();
    }

}
