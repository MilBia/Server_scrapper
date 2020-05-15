package com.scrapper.infections.repository;

import com.scrapper.Continect.Continent;
import com.scrapper.country.model.Country;
import com.scrapper.infections.model.Infection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InfectionRepository extends CrudRepository<Infection, Integer> {

    @Query("Select i" +
            " FROM Infection i" +
            " WHERE i.country = :country")
    List<Infection> findByCountry(@Param("country") Country country);

    @Query("Select i" +
            " FROM Infection i" +
            " WHERE i.country = :country" +
            " ORDER BY i.creationTime")
    List<Infection> findCurrentByCountry(@Param("country") Country country);

    @Query("Select i" +
            " FROM Infection i" +
            " WHERE i.country.continent = :continent")
    List<Infection> findByContinent(@Param("continent") Continent continent);
}
