package com.alumniportal.unmsm.service.impl;

import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.exception.AppException;
import com.alumniportal.unmsm.mapper.SkillMapper;
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

    private final SkillMapper skillMapper;


    @Override
    public List<SkillResponseDTO> findAll() {
        List<Skill> skillList = skillDAO.findAll();

        return skillMapper.entityListToDTOList(skillList);
    }

    @Override
    public SkillResponseDTO findById(Long id) {
        Skill skill = skillDAO.findById(id);
        if (skill == null) {
            throw new AppException("Skill not found", "NOT_FOUND");
        }
        return skillMapper.entityToDTO(skill);
    }

    @Override
    public void save(Skill skill) {
        skillDAO.save(skill);
    }

    @Override
    public void deleteById(Long id) {
        Skill skill = skillDAO.findById(id);
        if (skill == null) {
            throw new AppException("Skill not found", "NOT_FOUND");
        }
        skillDAO.deleteById(id);
    }

    @Override
    public List<SkillResponseDTO> findSkillsByUserId(Long userId) {
        List<Skill> skillsByUserId = skillDAO.findSkillsByUserId(userId);

        return skillMapper.entityListToDTOList(skillsByUserId);
    }

    public SkillResponseDTO saveSkill(SkillRequestDTO skillRequestDTO, Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AppException("User not found", "NOT_FOUND");
        }

        Skill skill = skillMapper.requestDtoToEntity(skillRequestDTO);

//        Asignando el usuario al skill y persistiendo
        skill.setUser(user);
        skillDAO.save(skill);
        return skillMapper.entityToDTO(skill);
    }

    @Override
    public SkillResponseDTO updateSkill(Long id, Map<String, Object> fields) {
        Skill skill = skillDAO.findById(id);
        if (skill == null) {
            throw new AppException("Skill not found", "NOT_FOUND");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Skill.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, skill, v);
        });
        skillDAO.save(skill);
        return skillMapper.entityToDTO(skill);
    }

}
