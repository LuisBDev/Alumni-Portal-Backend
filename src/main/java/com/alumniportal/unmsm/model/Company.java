package com.alumniportal.unmsm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblCompany")
public class Company extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ruc;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String sector;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String website;

    @Column(nullable = true)
    private String location;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = true)
    private LocalDate updatedAt;

    private String photoUrl;

    //    Relacion con JobOffer
    @OneToMany(mappedBy = "company", orphanRemoval = true)
    @JsonIgnore
    private List<JobOffer> jobOfferList;

}
