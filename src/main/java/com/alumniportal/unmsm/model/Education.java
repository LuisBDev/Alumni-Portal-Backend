package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tblEducation")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String fieldOfStudy;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = true)
    private String endDate;

    @Column(nullable = true)
    private String description;

    //    Relation with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
