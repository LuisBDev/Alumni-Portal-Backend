package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByEmail(String email);

    Company findByEmail(String email);

}
