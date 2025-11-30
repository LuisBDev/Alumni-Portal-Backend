package com.alumniportal.unmsm.persistence.interfaces;

import com.alumniportal.unmsm.model.CompanyFollower;

import java.util.List;

public interface ICompanyFollowerDAO {

    List<CompanyFollower> findAll();

    CompanyFollower findById(Long id);

    void save(CompanyFollower companyFollower);

    void deleteById(Long id);

    List<CompanyFollower> findFollowersByUserId(Long userId);

    List<CompanyFollower> findFollowersByCompanyId(Long companyId);

    boolean existsFollowerByUserIdAndCompanyId(Long userId, Long companyId);

    CompanyFollower findFollowerByUserIdAndCompanyId(Long userId, Long companyId);

    Long countFollowersByCompanyId(Long companyId);

    Long countFollowingByUserId(Long userId);
}

