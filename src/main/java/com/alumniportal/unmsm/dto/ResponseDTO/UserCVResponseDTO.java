package com.alumniportal.unmsm.dto.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCVResponseDTO {
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String email;
    private String career;
    private String contactNumber;
    private String about;


    private List<CertificationResponseDTO> certifications;
    private List<EducationResponseDTO> education;
    private List<ProjectResponseDTO> projects;
    private List<SkillResponseDTO> skills;
    private List<WorkExperienceResponseDTO> workExperience;
}
