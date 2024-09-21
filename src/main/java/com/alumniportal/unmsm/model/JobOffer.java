package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblJobOffer")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private Integer vacancies;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String nivel;

    @Column(nullable = false)
    private String modality;

    @Column(nullable = false)
    private Integer workload;

    @Column(nullable = false)
    private Integer minSalary;

    @Column(nullable = false)
    private Integer maxSalary;

    @Column(nullable = true)
    private Integer experience;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = true)
    private LocalDate updatedAt;


    //    Relation with Company
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


//    Relation with Application

//    Relation with Skill


}
