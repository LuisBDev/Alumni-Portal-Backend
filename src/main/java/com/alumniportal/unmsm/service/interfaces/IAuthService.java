package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.AuthCompanyResponse;
import com.alumniportal.unmsm.dto.AuthUserResponse;
import com.alumniportal.unmsm.dto.LoginRequestDTO;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;

public interface IAuthService {


    AuthUserResponse loginAcademic(LoginRequestDTO loginRequestDTO);

    AuthCompanyResponse loginCompany(LoginRequestDTO loginRequestDTO);

    AuthUserResponse registerAcademic(User user);

    AuthCompanyResponse registerCompany(Company company);
}
