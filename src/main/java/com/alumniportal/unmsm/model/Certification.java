package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tblCertification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String issuingOrganization;

    @Column
    private LocalDate issueDate;

    @Column(nullable = true)
    private LocalDate expirationDate;

    @Column(nullable = true)
    private String credentialUrl;

    //    Relation with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
