package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findApplicationsByUser_Id(Long userId);

    List<Application> findApplicationsByJobOffer_Id(Long jobOfferId);


}
