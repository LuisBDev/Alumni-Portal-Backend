package com.alumniportal.unmsm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String faculty;
    private String career;
    private String about;
    private String photoUrl;
    private String contactNumber;
    private String createdAt;
    private String updatedAt;
    private String plan;
    private String permanence;
    private String studentCode;
    private String headline;


}
