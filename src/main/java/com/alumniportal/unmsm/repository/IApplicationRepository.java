package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplicationRepository extends JpaRepository<Application, Long> {

    @Query("select a from Application a where a.user.id = ?1")
    List<Application> findApplicationsByUserId(Long userId);

    @Query("select a from Application a where a.jobOffer.id = ?1")
    List<Application> findApplicationsByJobOfferId(Long jobOfferId);

    @Query("select (count(a) > 0) from Application a where a.user.id = ?1 and a.jobOffer.id = ?2")
    boolean existsApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId);

    @Query("select a from Application a where a.user.id = ?1 and a.jobOffer.id = ?2")
    Application findApplicationByUserIdAndJobOfferId(Long userId, Long jobOfferId);


}
