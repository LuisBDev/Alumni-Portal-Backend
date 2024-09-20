package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tblWorkExperience")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = true)
    private String endDate;

    @Column(nullable = false)
    private String description;

//    Relation with Profile

}
