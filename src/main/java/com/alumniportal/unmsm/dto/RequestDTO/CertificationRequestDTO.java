package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificationRequestDTO {
    private String name;
    private String issuingOrganization;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialUrl;
}
