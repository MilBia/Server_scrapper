package com.scrapper.infections;

import com.scrapper.infections.model.Infection;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface InfectionService {

    Optional<Infection> getInfectionById(Integer countryId) throws IllegalArgumentException;

    Collection<Infection> getAllInfection();

    Infection addInfection(Infection country) throws IllegalArgumentException;
}
