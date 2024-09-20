package com.alumniportal.unmsm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Date startDate;

    @Column(nullable = true)
    private Date endDate;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String url;

    //    Relation with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


//    Relation with Enrollment


}
