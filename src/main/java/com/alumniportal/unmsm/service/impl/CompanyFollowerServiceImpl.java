package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.CompanyFollowerRequestDTO;
import com.alumniportal.unmsm.dto.response.CompanyFollowerResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.CompanyFollowerMapper;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.CompanyFollower;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyDAO;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyFollowerDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.ICompanyFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyFollowerServiceImpl implements ICompanyFollowerService {

    private final ICompanyFollowerDAO companyFollowerDAO;
    private final IUserDAO userDAO;
    private final ICompanyDAO companyDAO;
    private final CompanyFollowerMapper companyFollowerMapper;

    @Override
    @Transactional
    public CompanyFollowerResponseDTO followCompany(CompanyFollowerRequestDTO companyFollowerRequestDTO) {

        Long userId = companyFollowerRequestDTO.getUserId();
        Long companyId = companyFollowerRequestDTO.getCompanyId();

        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AppException("Company not found!", "NOT_FOUND");
        }

        if (companyFollowerDAO.existsFollowerByUserIdAndCompanyId(userId, companyId)) {
            throw new AppException("User already follows this company!", "CONFLICT");
        }

        CompanyFollower companyFollower = CompanyFollower.builder()
                .user(user)
                .company(company)
                .followedAt(LocalDate.now())
                .build();

        companyFollowerDAO.save(companyFollower);

        return companyFollowerMapper.entityToDTO(companyFollower);
    }

    @Override
    @Transactional
    public void unfollowCompany(CompanyFollowerRequestDTO companyFollowerRequestDTO) {

        Long userId = companyFollowerRequestDTO.getUserId();
        Long companyId = companyFollowerRequestDTO.getCompanyId();

        if (!companyFollowerDAO.existsFollowerByUserIdAndCompanyId(userId, companyId)) {
            throw new AppException("User does not follow this company!", "NOT_FOUND");
        }

        CompanyFollower companyFollower = companyFollowerDAO.findFollowerByUserIdAndCompanyId(userId, companyId);
        if (companyFollower == null) {
            throw new AppException("Follow relationship not found!", "NOT_FOUND");
        }

        companyFollowerDAO.deleteById(companyFollower.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyFollowerResponseDTO> getFollowedCompaniesByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }

        List<CompanyFollower> companyFollowers = companyFollowerDAO.findFollowersByUserId(userId);
        return companyFollowerMapper.entityListToDTOList(companyFollowers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyFollowerResponseDTO> getFollowersByCompanyId(Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AppException("Company not found!", "NOT_FOUND");
        }

        List<CompanyFollower> companyFollowers = companyFollowerDAO.findFollowersByCompanyId(companyId);
        return companyFollowerMapper.entityListToDTOList(companyFollowers);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserFollowingCompany(Long userId, Long companyId) {
        return companyFollowerDAO.existsFollowerByUserIdAndCompanyId(userId, companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countFollowersByCompanyId(Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AppException("Company not found!", "NOT_FOUND");
        }
        return companyFollowerDAO.countFollowersByCompanyId(companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countFollowingByUserId(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found!", "NOT_FOUND");
        }
        return companyFollowerDAO.countFollowingByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyFollowerResponseDTO> findAll() {
        List<CompanyFollower> companyFollowers = companyFollowerDAO.findAll();
        return companyFollowerMapper.entityListToDTOList(companyFollowers);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyFollowerResponseDTO findById(Long id) {
        CompanyFollower companyFollower = companyFollowerDAO.findById(id);
        if (companyFollower == null) {
            throw new AppException("Company follower not found!", "NOT_FOUND");
        }
        return companyFollowerMapper.entityToDTO(companyFollower);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        CompanyFollower companyFollower = companyFollowerDAO.findById(id);
        if (companyFollower == null) {
            throw new AppException("Company follower not found!", "NOT_FOUND");
        }
        companyFollowerDAO.deleteById(id);
    }
}
