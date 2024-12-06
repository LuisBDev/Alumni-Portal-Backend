package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.request.UserRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.response.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.request.LoginRequestDTO;
import com.alumniportal.unmsm.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {


    private final IAuthService authService;

    @PostMapping("/user/loginAcademic")
    public ResponseEntity<AuthUserResponseDTO> loginAcademic(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginAcademic(loginRequestDTO));
    }

    @PostMapping("/user/registerAcademic")
    public ResponseEntity<AuthUserResponseDTO> registerAcademic(@RequestBody UserRequestDTO userRequestDTO) {
        AuthUserResponseDTO authUserResponseDTO = authService.registerAcademic(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authUserResponseDTO);
    }

    @PostMapping("/company/loginCompany")
    public ResponseEntity<AuthCompanyResponseDTO> loginCompany(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginCompany(loginRequestDTO));
    }

    @PostMapping("/company/registerCompany")
    public ResponseEntity<AuthCompanyResponseDTO> registerCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        AuthCompanyResponseDTO authCompanyResponseDTO = authService.registerCompany(companyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authCompanyResponseDTO);
    }


}
