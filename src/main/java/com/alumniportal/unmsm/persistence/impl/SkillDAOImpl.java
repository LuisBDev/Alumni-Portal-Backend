package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.persistence.ISkillDAO;
import com.alumniportal.unmsm.repository.ISkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkillDAOImpl implements ISkillDAO {


    private final ISkillRepository skillRepository;


    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }

    @Override
    public List<Skill> findSkillsByUserId(Long userId) {
        return skillRepository.findSkillsByUserId(userId);
    }
}
