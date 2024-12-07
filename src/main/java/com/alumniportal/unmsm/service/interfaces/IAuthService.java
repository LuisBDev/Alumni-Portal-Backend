package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.CompanyRequestDTO;
import com.alumniportal.unmsm.dto.request.UserRequestDTO;
import com.alumniportal.unmsm.dto.response.AuthCompanyResponseDTO;
import com.alumniportal.unmsm.dto.response.AuthUserResponseDTO;
import com.alumniportal.unmsm.dto.request.LoginRequestDTO;

public interface IAuthService {


    AuthUserResponseDTO loginAcademic(LoginRequestDTO loginRequestDTO);

    AuthCompanyResponseDTO loginCompany(LoginRequestDTO loginRequestDTO);

    AuthUserResponseDTO registerAcademic(UserRequestDTO userRequestDTO);

    AuthCompanyResponseDTO registerCompany(CompanyRequestDTO companyRequestDTO);
}
