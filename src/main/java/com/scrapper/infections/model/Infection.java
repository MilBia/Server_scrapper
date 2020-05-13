package com.scrapper.infections.model;

import com.scrapper.country.model.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Infection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Country country;

    private LocalDateTime creationTime;

    private Integer totalCases;

    private Integer newCases;

    private Integer totalDeaths;

    private Integer newDeaths;

    private Integer totalRecovered;

    private Integer activeCases;

    private Integer seriousCritical;

    private Integer totCases1Mpop;

    private Integer deaths1Mpop;

    private Integer totalTests;

    private Integer tests1Mpop;

}
