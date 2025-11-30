package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.CompanyFollowerRequestDTO;
import com.alumniportal.unmsm.dto.response.CompanyFollowerResponseDTO;

import java.util.List;

public interface ICompanyFollowerService {

    CompanyFollowerResponseDTO followCompany(CompanyFollowerRequestDTO companyFollowerRequestDTO);

    void unfollowCompany(CompanyFollowerRequestDTO companyFollowerRequestDTO);

    List<CompanyFollowerResponseDTO> getFollowedCompaniesByUserId(Long userId);

    List<CompanyFollowerResponseDTO> getFollowersByCompanyId(Long companyId);

    boolean isUserFollowingCompany(Long userId, Long companyId);

    Long countFollowersByCompanyId(Long companyId);

    Long countFollowingByUserId(Long userId);

    List<CompanyFollowerResponseDTO> findAll();

    CompanyFollowerResponseDTO findById(Long id);

    void deleteById(Long id);
}
