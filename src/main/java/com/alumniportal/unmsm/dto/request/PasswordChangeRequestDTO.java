package com.alumniportal.unmsm.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PasswordChangeRequestDTO {

    private String email;
    private String password;
    private String newPassword;

}
