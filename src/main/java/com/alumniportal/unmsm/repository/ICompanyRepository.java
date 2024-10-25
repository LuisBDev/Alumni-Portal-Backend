package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long> {

    @Query("select (count(c) > 0) from Company c where c.email = ?1")
    boolean existsByEmail(String email);

    @Query("select c from Company c where c.email = ?1")
    Company findByEmail(String email);

}
