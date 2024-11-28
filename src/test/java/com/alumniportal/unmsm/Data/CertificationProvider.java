package com.alumniportal.unmsm.Data;

import com.alumniportal.unmsm.model.Certification;

import java.time.LocalDate;
import java.util.List;

public class CertificationProvider {

    public static List<Certification> certificationList() {
        return List.of(
                Certification.builder().id(1L).name("Python").issuingOrganization("Python Institute")
                        .issueDate(LocalDate.parse("2021-01-01")).expirationDate(LocalDate.parse("2021-01-01"))
                        .credentialUrl("www.google.com").build(),

                Certification.builder().id(2L).name("Java").issuingOrganization("Oracle")
                        .issueDate(LocalDate.parse("2021-01-01")).expirationDate(LocalDate.parse("2021-01-01"))
                        .credentialUrl("www.google.com").build(),

                Certification.builder().id(3L).name("C++").issuingOrganization("C++ Institute")
                        .issueDate(LocalDate.parse("2021-01-01")).expirationDate(LocalDate.parse("2021-01-01"))
                        .credentialUrl("www.google.com").build()
        );
    }

    public static Certification certificationOne() {
        return Certification.builder().id(1L).name("Python").issuingOrganization("Python Institute")
                .issueDate(LocalDate.parse("2021-01-01")).expirationDate(LocalDate.parse("2021-01-01"))
                .credentialUrl("www.google.com").build();
    }

}
