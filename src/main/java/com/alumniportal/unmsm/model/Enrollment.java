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
@Table(name = "tblEnrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    Mapping user and activity
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column
    private LocalDate enrollmentDate;

    @Column(nullable = true)
    private String status;


}
