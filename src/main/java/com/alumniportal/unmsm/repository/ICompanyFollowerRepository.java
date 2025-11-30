package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.CompanyFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyFollowerRepository extends JpaRepository<CompanyFollower, Long> {

    @Query("select cf from CompanyFollower cf where cf.user.id = ?1")
    List<CompanyFollower> findFollowersByUserId(Long userId);

    @Query("select cf from CompanyFollower cf where cf.company.id = ?1")
    List<CompanyFollower> findFollowersByCompanyId(Long companyId);

    @Query("select (count(cf) > 0) from CompanyFollower cf where cf.user.id = ?1 and cf.company.id = ?2")
    boolean existsFollowerByUserIdAndCompanyId(Long userId, Long companyId);

    @Query("select cf from CompanyFollower cf where cf.user.id = ?1 and cf.company.id = ?2")
    CompanyFollower findFollowerByUserIdAndCompanyId(Long userId, Long companyId);

    @Query("select count(cf) from CompanyFollower cf where cf.company.id = ?1")
    Long countFollowersByCompanyId(Long companyId);

    @Query("select count(cf) from CompanyFollower cf where cf.user.id = ?1")
    Long countFollowingByUserId(Long userId);
}

