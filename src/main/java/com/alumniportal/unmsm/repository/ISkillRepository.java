package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findSkillsByUser_Id(Long userId);
}
