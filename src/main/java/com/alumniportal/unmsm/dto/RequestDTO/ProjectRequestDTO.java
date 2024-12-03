package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequestDTO {

    private String name;
    private LocalDate date;
    private String description;
}
