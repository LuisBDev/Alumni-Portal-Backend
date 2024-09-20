package com.alumniportal.unmsm.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column
    private String email;

    @Column
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


//    Relacion con User, WorkExperience, Education, Project, Certification, Skill, Interest, Enrollment, Activity, Application


}
