package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.SkillDTO;
import com.alumniportal.unmsm.model.Skill;

import java.util.List;
import java.util.Map;

public interface ISkillService {
    List<SkillDTO> findAll();

    SkillDTO findById(Long id);

    void save(Skill skill);

    void deleteById(Long id);

    //    Buscar todos los skills de un usuario
    List<SkillDTO> findSkillsByUserId(Long userId);

    public void saveSkill(Skill skill, Long userId);

    void updateSkill(Long id, Map<String, Object> fields);

}
