package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.AuthCompanyResponse;
import com.alumniportal.unmsm.dto.AuthResponseDTO;
import com.alumniportal.unmsm.dto.AuthUserResponse;
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
    public ResponseEntity<AuthUserResponse> loginAcademic(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginAcademic(loginRequestDTO));
    }

    @PostMapping("/user/registerAcademic")
    public ResponseEntity<AuthUserResponse> registerAcademic(@RequestBody User user) {
        return ResponseEntity.ok(authService.registerAcademic(user));
    }

    @PostMapping("/company/loginCompany")
    public ResponseEntity<AuthCompanyResponse> loginCompany(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginCompany(loginRequestDTO));
    }

    @PostMapping("/company/registerCompany")
    public ResponseEntity<AuthCompanyResponse> registerCompany(@RequestBody Company company) {
        return ResponseEntity.ok(authService.registerCompany(company));
    }


}
