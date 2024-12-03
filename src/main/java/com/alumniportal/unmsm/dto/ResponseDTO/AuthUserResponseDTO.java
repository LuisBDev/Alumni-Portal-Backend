package com.alumniportal.unmsm.dto.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponseDTO {
    
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
    private String role;
    private String token;

}
