package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblProject")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    //    Relation with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
