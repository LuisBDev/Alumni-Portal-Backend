package com.alumniportal.unmsm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFollowerResponseDTO {

    private Long id;
    private LocalDate followedAt;

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPaternalSurname;
    private String userMaternalSurname;

    private Long companyId;
    private String companyName;
    private String companyEmail;
    private String companyRuc;
    private String companySector;
    private String companyPhotoUrl;
}

