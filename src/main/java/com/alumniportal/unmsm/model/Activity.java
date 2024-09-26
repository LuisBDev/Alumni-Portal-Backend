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
@Table(name = "tblActivity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

//    event_type ENUM('workshop', 'conference', 'seminar', 'post', 'other')

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = true)
    private LocalDate startDate;

    @Column(nullable = true)
    private LocalDate endDate;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String url;

    @Column(nullable = false)
    private boolean enrollable;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = true)
    private LocalDate updatedAt;

    //    Relation with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //    Relation with Enrollment
    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private List<Enrollment> enrollmentList;


}
