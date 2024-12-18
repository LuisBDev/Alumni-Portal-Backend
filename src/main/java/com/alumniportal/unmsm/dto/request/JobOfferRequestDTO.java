package com.alumniportal.unmsm.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobOfferRequestDTO {

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

}



