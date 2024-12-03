package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequestDTO {


    private String name;
    private String password;
    private String ruc;
    private String email;
    private String description;
    private String sector;
}
