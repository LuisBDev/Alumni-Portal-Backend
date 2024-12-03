package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.ResponseDTO.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.RequestDTO.LoginRequestDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;

public interface IAuthService {


    AuthUserResponseDTO loginAcademic(LoginRequestDTO loginRequestDTO);

    AuthCompanyResponseDTO loginCompany(LoginRequestDTO loginRequestDTO);

    AuthUserResponseDTO registerAcademic(User user);

    AuthCompanyResponseDTO registerCompany(Company company);
}
