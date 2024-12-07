package com.alumniportal.unmsm.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillResponseDTO {

    private Long id;
    private String name;
    private String level;

    private Long userId;
}
