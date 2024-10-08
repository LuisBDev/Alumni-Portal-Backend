package com.alumniportal.unmsm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO {

    private Long id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long userId;

}
