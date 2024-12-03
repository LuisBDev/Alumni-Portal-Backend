package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.ResponseDTO.SkillResponseDTO;
import com.alumniportal.unmsm.model.Skill;

import java.util.List;
import java.util.Map;

public interface ISkillService {
    List<SkillResponseDTO> findAll();

    SkillResponseDTO findById(Long id);

    void save(Skill skill);

    void deleteById(Long id);

    //    Buscar todos los skills de un usuario
    List<SkillResponseDTO> findSkillsByUserId(Long userId);

    public void saveSkill(Skill skill, Long userId);

    void updateSkill(Long id, Map<String, Object> fields);

}