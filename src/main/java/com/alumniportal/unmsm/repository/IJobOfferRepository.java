package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobOfferRepository extends JpaRepository<JobOffer, Long> {

    @Query("select j from JobOffer j where j.company.id = ?1")
    List<JobOffer> findJobOffersByCompanyId(Long companyId);

}
