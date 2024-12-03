package com.alumniportal.unmsm.dto.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

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
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String plan;
    private String permanence;
    private String studentCode;
    private String headline;
    private String role;


}
