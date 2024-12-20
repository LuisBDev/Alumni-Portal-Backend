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
@Table(name = "tblApplication")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate applicationDate;

    @Column
    private String status;


    //    para mapear cuando un User aplica a un JobOffer
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //    para mapear cuando un JobOffer recibe una aplicacion
    @ManyToOne
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;


}
