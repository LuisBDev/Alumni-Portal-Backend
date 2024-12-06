package com.alumniportal.unmsm.data;

import com.alumniportal.unmsm.model.Company;

import java.util.List;

public class CompanyProvider {

    public static List<Company> companyList() {
        return List.of(
                Company.builder()
                        .id(1L)
                        .name("Company 1")
                        .email("email1@gmail.com")
                        .password("password1")
                        .build(),
                Company.builder()
                        .id(2L)
                        .name("Company 2")
                        .email("email2@gmail.com")
                        .password("password2")
                        .build(),
                Company.builder()
                        .id(3L)
                        .name("Company 3")
                        .email("email3@gmail.com")
                        .password("password3")
                        .build()
        );

    }

    public static Company companyOne() {
        return Company.builder()
                .id(1L)
                .name("Company 1")
                .email("company@gmail.com")
                .password("password")
                .build();
    }

}
