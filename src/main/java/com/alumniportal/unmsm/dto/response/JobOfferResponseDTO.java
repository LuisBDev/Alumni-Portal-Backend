package com.alumniportal.unmsm.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Integer vacancies;
    private String area;
    private String nivel;
    private String modality;
    private Integer workload;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer experience;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Long companyId;
    private String companyName;
    private String companyRuc;
    private String companyEmail;
    private String companyPhone;
}
