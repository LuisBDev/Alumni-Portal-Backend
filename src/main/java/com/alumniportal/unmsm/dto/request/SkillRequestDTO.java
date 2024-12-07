package com.alumniportal.unmsm.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillRequestDTO {

    private Long id;
    private String name;
    private String level;
}
