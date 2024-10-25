package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Long> {

    @Query("select s from Skill s where s.user.id = ?1")
    List<Skill> findSkillsByUserId(Long userId);
}
