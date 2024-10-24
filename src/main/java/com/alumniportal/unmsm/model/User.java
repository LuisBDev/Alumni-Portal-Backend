package com.alumniportal.unmsm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tblUser")
public class User extends AbstractEntity {


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
    private LocalDate createdAt;

    @Column(nullable = true)
    private LocalDate updatedAt;

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

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<WorkExperience> workExperienceList;    //    Relacion con WorkExperience


    //    Relacion con Education
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Education> educationList;

    //    Relacion con Certification
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Certification> certificationList;

    //    Relacion con Project
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Project> projectList;

    //    Relacion con Activity
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Activity> activityList;

    //    Relacion con Enrollment
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Enrollment> enrollmentList;

    //    Relacion con Application
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Application> applicationList;

    //    Relacion con Skill
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Skill> skillList;


}
