package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String issueDate;

    @Column
    private String expirationDate;

    @Column
    private String credentialUrl;

//    Relation with Profile


}
