package com.alumniportal.unmsm.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

