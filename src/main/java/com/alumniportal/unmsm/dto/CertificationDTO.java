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
public class CertificationDTO {
    private Long id;
    private String name;
    private String issuingOrganization;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialUrl;
    private Long userId;  // Solo el ID del usuario en lugar de la entidad completa
}
