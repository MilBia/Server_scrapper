package com.scrapper.country.repository;

import com.scrapper.continect.Continent;
import com.scrapper.country.model.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    @Query("Select c" +
            " FROM Country c" +
            " WHERE c.name = :country_name")
    List<Country> getCountryByName(@Param("country_name") String country_name);

    @Query("Select c" +
            " FROM Country c" +
            " WHERE c.continent = :continent")
    List<Country> findByContinent(@Param("continent") Continent continent);
}
