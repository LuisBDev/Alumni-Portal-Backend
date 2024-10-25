package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.AuthResponseDTO;
import com.alumniportal.unmsm.dto.LoginRequestDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.IAuthService;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class AuthController {


    @Autowired
    private IAuthService authService;

    @PostMapping("/user/loginAcademic")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO, User.class));
    }


    @PostMapping("/company/loginCompany")
    public ResponseEntity<AuthResponseDTO> loginCompany(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO, Company.class));
    }


    @PostMapping("/user/registerAcademic")
    public ResponseEntity<?> registerAcademic(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user, User.class));
    }

    @PostMapping("/company/registerCompany")
    public ResponseEntity<?> registerCompany(@RequestBody Company company) {
        return ResponseEntity.ok(authService.register(company, Company.class));
    }


}
