package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICertificationRepository extends JpaRepository<Certification, Long> {

    List<Certification> findCertificationsByUser_Id(Long userId);

}
