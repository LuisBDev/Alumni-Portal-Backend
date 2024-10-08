package com.alumniportal.unmsm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    private Long id;
    private LocalDate applicationDate;
    private Long userId;       // Solo el ID del User en lugar de la entidad completa
    private Long jobOfferId;   // Solo el ID del JobOffer en lugar de la entidad completa
}
