package com.alumniportal.unmsm.dto.ResponseDTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String eventType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String url;
    private boolean enrollable;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userRole;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPaternalSurname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userMaternalSurname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyRole;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyRuc;
}
