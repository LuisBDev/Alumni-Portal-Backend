package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.RequestDTO.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.RequestDTO.UserRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.RequestDTO.LoginRequestDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(authService.registerAcademic(userRequestDTO));
    }

    @PostMapping("/company/loginCompany")
    public ResponseEntity<AuthCompanyResponseDTO> loginCompany(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.loginCompany(loginRequestDTO));
    }

    @PostMapping("/company/registerCompany")
    public ResponseEntity<AuthCompanyResponseDTO> registerCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return ResponseEntity.ok(authService.registerCompany(companyRequestDTO));
    }


}
