package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.response.CompanyResponseDTO;
import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
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
    public ResponseEntity<List<CompanyResponseDTO>> findAll() {
        List<CompanyResponseDTO> companyResponseDTOList = companyService.findAll();
        return ResponseEntity.ok(companyResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> findById(@PathVariable Long id) {
        CompanyResponseDTO companyResponseDTO = companyService.findById(id);
        return ResponseEntity.ok(companyResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CompanyResponseDTO> findByEmail(@RequestBody String email) {
        CompanyResponseDTO companyResponseDTO = companyService.findByEmail(email);
        return ResponseEntity.ok(companyResponseDTO);
    }

    //Actualizar parcialmente los campos
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        companyService.updateCompany(id, updates);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        companyService.updatePassword(id, passwordChangeRequestDTO);
        return ResponseEntity.noContent().build();
    }

}
