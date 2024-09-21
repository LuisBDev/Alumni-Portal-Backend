package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findJobOffersByCompany_Id(Long companyId);

}
