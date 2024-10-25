package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICertificationRepository extends JpaRepository<Certification, Long> {

    @Query("select c from Certification c where c.user.id = ?1")
    List<Certification> findCertificationsByUserId(Long userId);

}
