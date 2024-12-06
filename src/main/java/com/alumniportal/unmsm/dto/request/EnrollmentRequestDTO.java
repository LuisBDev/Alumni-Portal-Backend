package com.alumniportal.unmsm.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentRequestDTO {

    @NotNull
    private UserRequestDTO user;

    @NotNull
    private ActivityRequestDTO activity;


}
