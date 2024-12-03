package com.alumniportal.unmsm.dto.RequestDTO;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentRequestDTO {

    @NotNull
    private UserEnrollment user;

    @NotNull
    private ActivityEnrollment activity;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserEnrollment {
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActivityEnrollment {
        @NotNull
        private Long id;
    }
}
