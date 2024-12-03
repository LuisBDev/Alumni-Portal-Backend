package com.alumniportal.unmsm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(columnDefinition = "TEXT")
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
    @OneToMany(mappedBy = "jobOffer", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Application> applicationList;

//    Relation with Skill


}
