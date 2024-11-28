package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.CompanyDTO;
import com.alumniportal.unmsm.dto.PasswordChangeDTO;
import com.alumniportal.unmsm.service.interfaces.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    private final ICompanyService companyService;

    @GetMapping("/all")
    public List<CompanyDTO> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable Long id) {
        return companyService.findById(id);
    }

//    @PostMapping("/loginCompany")
//    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
//        try {
//            CompanyDTO companyDTO = companyService.validateLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
//            return ResponseEntity.ok(companyDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }
//
//    @PostMapping("/registerCompany")
//    public ResponseEntity<?> save(@RequestBody Company company) {
//        try {
//            companyService.saveCompany(company);
//            return ResponseEntity.ok("Company registered successfully!");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        CompanyDTO companyDTO = companyService.findById(id);
        if (companyDTO == null) {
            return ResponseEntity.badRequest().body("Error: Company not found!");
        }
        companyService.deleteById(id);
        return ResponseEntity.ok("Company deleted successfully!");
    }

    @GetMapping
    public CompanyDTO findByEmail(@RequestBody String email) {
        return companyService.findByEmail(email);
    }

    //Actualizar parcialmente los campos
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            companyService.updateCompany(id, updates);
            return ResponseEntity.ok("Company updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        try {
            companyService.updatePassword(id, passwordChangeDTO);
            return ResponseEntity.ok("Password updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
