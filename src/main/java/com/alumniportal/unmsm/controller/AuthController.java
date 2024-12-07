package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.request.UserRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.response.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.request.LoginRequestDTO;
import com.alumniportal.unmsm.service.interfaces.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Login as academic user")
    @ApiResponse(responseCode = "200", description = "Academic user authenticated")
    public ResponseEntity<AuthUserResponseDTO> loginAcademic(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginAcademic(loginRequestDTO));
    }

    @PostMapping("/user/registerAcademic")
    @Operation(summary = "Register academic user")
    @ApiResponse(responseCode = "201", description = "Return academic user created")
    public ResponseEntity<AuthUserResponseDTO> registerAcademic(@RequestBody UserRequestDTO userRequestDTO) {
        AuthUserResponseDTO authUserResponseDTO = authService.registerAcademic(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authUserResponseDTO);
    }

    @PostMapping("/company/loginCompany")
    @Operation(summary = "Login as company")
    @ApiResponse(responseCode = "200", description = "Company authenticated")
    public ResponseEntity<AuthCompanyResponseDTO> loginCompany(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginCompany(loginRequestDTO));
    }

    @PostMapping("/company/registerCompany")
    @Operation(summary = "Register company")
    @ApiResponse(responseCode = "201", description = "Return company created")
    public ResponseEntity<AuthCompanyResponseDTO> registerCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        AuthCompanyResponseDTO authCompanyResponseDTO = authService.registerCompany(companyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authCompanyResponseDTO);
    }


}
