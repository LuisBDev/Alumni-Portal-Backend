package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExperienceRequestDTO {

    private String company;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

}
