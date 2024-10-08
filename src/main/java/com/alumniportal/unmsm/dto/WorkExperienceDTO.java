package com.alumniportal.unmsm.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExperienceDTO {

    private Long id;
    private String company;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    private Long userId;
}
