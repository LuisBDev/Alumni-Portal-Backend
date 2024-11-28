package com.alumniportal.unmsm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeDTO {

    private String email;
    private String password;
    private String newPassword;

}
