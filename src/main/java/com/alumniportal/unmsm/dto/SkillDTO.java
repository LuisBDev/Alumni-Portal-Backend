package com.alumniportal.unmsm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDTO {

    private Long id;
    private String name;
    private String level;

    private Long userId;
}
