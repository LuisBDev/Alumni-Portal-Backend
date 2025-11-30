package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.CompanyFollower;
import com.alumniportal.unmsm.persistence.interfaces.ICompanyFollowerDAO;
import com.alumniportal.unmsm.repository.ICompanyFollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyFollowerDAOImpl implements ICompanyFollowerDAO {

    private final ICompanyFollowerRepository companyFollowerRepository;

    @Override
    public List<CompanyFollower> findAll() {
        return companyFollowerRepository.findAll();
    }

    @Override
    public CompanyFollower findById(Long id) {
        return companyFollowerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(CompanyFollower companyFollower) {
        companyFollowerRepository.save(companyFollower);
    }

    @Override
    public void deleteById(Long id) {
        companyFollowerRepository.deleteById(id);
    }

    @Override
    public List<CompanyFollower> findFollowersByUserId(Long userId) {
        return companyFollowerRepository.findFollowersByUserId(userId);
    }

    @Override
    public List<CompanyFollower> findFollowersByCompanyId(Long companyId) {
        return companyFollowerRepository.findFollowersByCompanyId(companyId);
    }

    @Override
    public boolean existsFollowerByUserIdAndCompanyId(Long userId, Long companyId) {
        return companyFollowerRepository.existsFollowerByUserIdAndCompanyId(userId, companyId);
    }

    @Override
    public CompanyFollower findFollowerByUserIdAndCompanyId(Long userId, Long companyId) {
        return companyFollowerRepository.findFollowerByUserIdAndCompanyId(userId, companyId);
    }

    @Override
    public Long countFollowersByCompanyId(Long companyId) {
        return companyFollowerRepository.countFollowersByCompanyId(companyId);
    }

    @Override
    public Long countFollowingByUserId(Long userId) {
        return companyFollowerRepository.countFollowingByUserId(userId);
    }
}

