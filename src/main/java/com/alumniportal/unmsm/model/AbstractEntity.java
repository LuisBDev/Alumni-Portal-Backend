package com.alumniportal.unmsm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class AbstractEntity {
    private Long id;
    private String name;
    private String photoUrl;
    private String email;
    private String password;
    private Role role;
    private LocalDate createdAt;
}