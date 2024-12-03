package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRequestDTO {

    private String title;
    private String description;
    private String eventType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String url;
    private boolean enrollable;

}
