package com.alumniportal.unmsm.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private Long userId;
}
