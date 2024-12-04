package com.alumniportal.unmsm.dto.ResponseDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCompanyResponseDTO {
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
    private String role;
    private String token;

}
