package com.alumniportal.unmsm.mapper;

import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.model.Skill;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkillMapper {

    private final ModelMapper modelMapper;

    public SkillResponseDTO entityToDTO(Skill skill) {
        return modelMapper.map(skill, SkillResponseDTO.class);
    }

    public List<SkillResponseDTO> entityListToDTOList(List<Skill> skills) {
        return skills.stream()
                .map(this::entityToDTO)
                .toList();
    }

    public Skill dtoToEntity(SkillResponseDTO skillResponseDTO) {
        return modelMapper.map(skillResponseDTO, Skill.class);
    }

    public List<Skill> dtoListToEntityList(List<SkillResponseDTO> skillResponseDTOS) {
        return skillResponseDTOS.stream()
                .map(this::dtoToEntity)
                .toList();
    }

    public Skill requestDtoToEntity(SkillRequestDTO skillRequestDTO) {
        return modelMapper.map(skillRequestDTO, Skill.class);
    }

}
