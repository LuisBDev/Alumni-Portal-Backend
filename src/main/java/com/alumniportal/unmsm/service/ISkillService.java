package com.alumniportal.unmsm.service;

import com.alumniportal.unmsm.dto.SkillDTO;
import com.alumniportal.unmsm.model.Skill;

import java.util.List;

public interface ISkillService {
    List<SkillDTO> findAll();

    SkillDTO findById(Long id);

    void save(Skill skill);

    void deleteById(Long id);

    //    Buscar todos los skills de un usuario
    List<SkillDTO> findSkillsByUser_Id(Long userId);

    public void saveSkill(Skill skill, Long userId);

}
