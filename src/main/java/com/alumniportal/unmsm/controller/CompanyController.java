package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @GetMapping("/all")
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company findById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @PostMapping("/loginCompany")
    public ResponseEntity<?> login(@RequestBody Company company) {
        Company companyFound = companyService.findByEmail(company.getEmail());
        if (companyFound == null) {
            return ResponseEntity.badRequest().body("Error: Email not found!");
        } else {
            if (companyFound.getPassword().equals(company.getPassword())) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.badRequest().body("Error: Password incorrect!");
            }
        }
    }


    @PostMapping("/registerCompany")
    public ResponseEntity<?> save(@RequestBody Company company) {
        boolean companyDB = companyService.existsByEmail(company.getEmail());
        if (companyDB) {
            return ResponseEntity.badRequest().body("Error: Email Company already exists!");
        }
        company.setCreatedAt(LocalDate.now());
        companyService.save(company);
        return ResponseEntity.ok("Company saved successfully!");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return ResponseEntity.badRequest().body("Error: Company not found!");
        }
        companyService.deleteById(id);
        return ResponseEntity.ok("Company deleted successfully!");
    }

    @GetMapping
    public Company findByEmail(@RequestBody String email) {
        return companyService.findByEmail(email);
    }


}
