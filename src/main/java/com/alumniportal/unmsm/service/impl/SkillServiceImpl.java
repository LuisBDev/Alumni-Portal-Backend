package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.SkillDTO;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ISkillDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import com.alumniportal.unmsm.service.ISkillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements ISkillService {

    @Autowired
    private ISkillDAO skillDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<SkillDTO> findAll() {
        return skillDAO.findAll()
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .toList();
    }

    @Override
    public SkillDTO findById(Long id) {
        Skill skill = skillDAO.findById(id);
        if (skill == null) {
            return null;
        }
        return modelMapper.map(skill, SkillDTO.class);
    }

    @Override
    public void save(Skill skill) {
        skillDAO.save(skill);
    }

    @Override
    public void deleteById(Long id) {
        skillDAO.deleteById(id);
    }

    @Override
    public List<SkillDTO> findSkillsByUser_Id(Long userId) {
        return skillDAO.findSkillsByUser_Id(userId)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .toList();
    }

    public void saveSkill(Skill skill, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
//        Asignando el usuario al skill y persistiendo
        skill.setUser(user);
        skillDAO.save(skill);
//        Agregando el skill a la lista de skills del usuario
        user.getSkillList().add(skill);
        userDAO.save(user);
    }

}
