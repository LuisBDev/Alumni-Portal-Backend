package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private String email;
    private String password;
    private String paternalSurname;
    private String maternalSurname;
    private String name;
    private String faculty;
    private String career;
    private String plan;
    private String permanence;
    private String studentCode;

}
