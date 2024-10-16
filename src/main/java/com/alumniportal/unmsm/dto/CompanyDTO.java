package com.alumniportal.unmsm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;
    private String ruc;
    private String email;
    private String description;
    private String sector;
    private String phone;
    private String website;
    private String location;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String photoUrl;
}
