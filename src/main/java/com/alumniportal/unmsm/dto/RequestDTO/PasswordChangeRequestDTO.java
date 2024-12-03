package com.alumniportal.unmsm.dto.RequestDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeRequestDTO {

    private String email;
    private String password;
    private String newPassword;

}
