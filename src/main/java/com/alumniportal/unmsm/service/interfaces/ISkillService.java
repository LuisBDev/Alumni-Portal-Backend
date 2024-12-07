package com.alumniportal.unmsm.service.interfaces;

import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
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

    SkillResponseDTO saveSkill(SkillRequestDTO skillRequestDTO, Long userId);

    SkillResponseDTO updateSkill(Long id, Map<String, Object> fields);

}
