package com.alumniportal.unmsm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserCVDTO {
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String email;
    private String career;
    private String contactNumber;
    private String about;

    private List<CertificationDTO> certifications;
    private List<EducationDTO> education;
    private List<ProjectDTO> projects;
    private List<SkillDTO> skills;
    private List<WorkExperienceDTO> workExperience;
}
