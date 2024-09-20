package com.alumniportal.unmsm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tblUser")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String paternalSurname;

    @Column
    private String maternalSurname;

    @Column
    private String name;

    @Column
    private String about;

    @Column
    private String photoUrl;

    @Column
    private String contactNumber;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = true)
    private Date updatedAt;

    @Column
    private String faculty;

    @Column
    private String career;

    @Column
    private String plan;

    @Column
    private String permanence;

    @Column
    private String studentCode;

    @Column(nullable = true)
    private String headline;


//    Relacion con WorkExperience, Education, Project, Certification, Skill, Interest, Enrollment, Activity, Application

    //    Relacion con WorkExperience
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<WorkExperience> workExperiences;


}
