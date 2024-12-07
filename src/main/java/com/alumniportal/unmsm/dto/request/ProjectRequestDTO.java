package com.alumniportal.unmsm.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequestDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private String description;
}
