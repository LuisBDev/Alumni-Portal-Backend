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
public class ApplicationRequestDTO {

    @NotNull
    private UserApplication user;

    @NotNull
    private JobOfferApplication jobOffer;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserApplication {
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JobOfferApplication {
        @NotNull
        private Long id;
    }
}
