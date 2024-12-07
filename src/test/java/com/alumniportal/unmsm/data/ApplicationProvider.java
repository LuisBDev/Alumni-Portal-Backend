package com.alumniportal.unmsm.data;

import com.alumniportal.unmsm.model.Application;
import com.alumniportal.unmsm.model.JobOffer;
import com.alumniportal.unmsm.model.User;

import java.time.LocalDate;
import java.util.List;

public class ApplicationProvider {

    public static List<Application> applicationList() {
        return List.of(
                Application.builder()
                        .id(1L)
                        .applicationDate(LocalDate.parse("2021-08-01"))
                        .status("PENDING")
                        .user(User.builder()
                                .id(1L)
                                .name("User 1")
                                .paternalSurname("Surname 1")
                                .maternalSurname("Surname 2")
                                .email("email@gmail.com")
                                .build())
                        .jobOffer(JobOffer.builder()
                                .id(1L)
                                .title("Job Offer 1")
                                .description("Description 1")
                                .build())
                        .build(),

                Application.builder()
                        .id(2L)
                        .applicationDate(LocalDate.parse("2021-08-02"))
                        .status("REJECTED")
                        .user(User.builder()
                                .id(2L)
                                .name("User 2")
                                .paternalSurname("Surname 3")
                                .maternalSurname("Surname 4")
                                .email("email2@gmail.com")
                                .build())
                        .jobOffer(JobOffer.builder()
                                .id(2L)
                                .title("Job Offer 2")
                                .description("Description 2")
                                .build())
                        .build()
        );
    }

    public static Application applicationOne() {
        return Application.builder()
                .id(1L)
                .applicationDate(LocalDate.parse("2021-08-01"))
                .status("PENDING")
                .user(User.builder()
                        .id(1L)
                        .name("User 1")
                        .paternalSurname("Surname 1")
                        .maternalSurname("Surname 2")
                        .email("test@gmail.com")
                        .build())
                .jobOffer(JobOffer.builder()
                        .id(1L)
                        .title("Job Offer 1")
                        .description("Description 1")
                        .build())
                .build();
    }

}
