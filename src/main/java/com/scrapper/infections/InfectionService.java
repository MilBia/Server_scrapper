package com.scrapper.infections;

import com.scrapper.Continect.Continent;
import com.scrapper.country.model.Country;
import com.scrapper.infections.model.Infection;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface InfectionService {

    Optional<Infection> getInfectionById(Integer infectionId) throws IllegalArgumentException;

    Collection<Infection> getAllInfectionByCountry(Country country) throws IllegalArgumentException;

    Infection getCurrentInfectionByCountry(Country country) throws IllegalArgumentException;

    Collection<Infection> getAllInfectionForContinent(Continent continent);

    Collection<Infection> getCurrentInfectionByContinent(Continent continent) throws IllegalArgumentException;

    Collection<Infection> getAllInfection();

    Collection<Infection> addNewInfections();

    Infection addInfection(Infection country) throws IllegalArgumentException;
}
