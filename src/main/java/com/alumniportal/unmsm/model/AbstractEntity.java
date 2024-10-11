package com.alumniportal.unmsm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntity {
    private Long id;
    private String name;
    private String photoUrl;
}