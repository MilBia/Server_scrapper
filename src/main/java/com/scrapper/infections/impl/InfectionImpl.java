package com.scrapper.infections.impl;

import com.scrapper.infections.InfectionService;
import com.scrapper.infections.model.Infection;
import com.scrapper.infections.repository.InfectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class InfectionImpl implements InfectionService {

    @Autowired
    private InfectionRepository infectionRepository;

    @Override
    public Optional<Infection> getInfectionById(Integer countryId) throws IllegalArgumentException {
        return infectionRepository.findById(countryId);
    }

    @Override
    public Collection<Infection> getAllInfection() {
        return StreamSupport.stream(infectionRepository.findAll().spliterator(), false).
                collect(Collectors.toList());
    }

    @Override
    public Infection addInfection(Infection country) throws IllegalArgumentException {
        if(country.getId() == null || !infectionRepository.existsById(country.getId())) {
            try {
                return infectionRepository.save(country);
            }
            catch (DataAccessException e){
                throw  new IllegalArgumentException(e);
            }
        }else
            throw new IllegalArgumentException("Table country already exist");
    }
}
