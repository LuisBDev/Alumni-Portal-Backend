package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.ResponseDTO.SkillResponseDTO;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.interfaces.ISkillDAO;
import com.alumniportal.unmsm.persistence.interfaces.IUserDAO;
import com.alumniportal.unmsm.service.interfaces.ISkillService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements ISkillService {

    private final ISkillDAO skillDAO;

    private final IUserDAO userDAO;

    private final ModelMapper modelMapper;


    @Override
    public List<SkillResponseDTO> findAll() {
        return skillDAO.findAll()
                .stream()
                .map(skill -> modelMapper.map(skill, SkillResponseDTO.class))
                .toList();
    }

    @Override
    public SkillResponseDTO findById(Long id) {
        Skill skill = skillDAO.findById(id);
        if (skill == null) {
            return null;
        }
        return modelMapper.map(skill, SkillResponseDTO.class);
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
    public List<SkillResponseDTO> findSkillsByUserId(Long userId) {
        return skillDAO.findSkillsByUserId(userId)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillResponseDTO.class))
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

    @Override
    public void updateSkill(Long id, Map<String, Object> fields) {
        Skill skill = skillDAO.findById(id);
        if (skill == null) {
            throw new RuntimeException("Error: skill not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Skill.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, skill, v);
        });
        skillDAO.save(skill);
    }

}
