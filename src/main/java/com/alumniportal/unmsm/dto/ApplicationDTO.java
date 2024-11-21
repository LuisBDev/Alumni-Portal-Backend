package com.alumniportal.unmsm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    private Long id;

    private Long userId;
    private String userStudentCode;
    private String userName;
    private String userPaternalSurname;
    private String userMaternalSurname;
    private String userEmail;
    private String userFaculty;
    private String userCareer;

    private LocalDate applicationDate;
    private String status;

    private Long jobOfferId;
    private String jobOfferTitle;

}
