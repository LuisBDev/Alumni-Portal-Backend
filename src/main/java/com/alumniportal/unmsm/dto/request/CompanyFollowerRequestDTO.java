package com.alumniportal.unmsm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFollowerRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long companyId;

}
