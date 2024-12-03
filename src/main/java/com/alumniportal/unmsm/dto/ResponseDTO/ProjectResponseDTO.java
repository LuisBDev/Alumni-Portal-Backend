package com.alumniportal.unmsm.dto.ResponseDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private Long userId;
}
