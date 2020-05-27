package com.scrapper.infections.impl;

import com.scrapper.Continect.Continent;
import com.scrapper.country.model.Country;
import com.scrapper.country.repository.CountryRepository;
import com.scrapper.infections.InfectionService;
import com.scrapper.infections.model.Infection;
import com.scrapper.infections.repository.InfectionRepository;
import com.scrapper.utilities.Scrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class InfectionImpl implements InfectionService {

    @Autowired
    private InfectionRepository infectionRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Optional<Infection> getInfectionById(Integer infectionId) throws IllegalArgumentException {
        return infectionRepository.findById(infectionId);
    }

    @Override
    public Collection<Infection> getAllInfectionByCountry(Country country) throws IllegalArgumentException {
        return infectionRepository.findByCountry(country);
    }

    @Override
    public Infection getCurrentInfectionByCountry(Country country) throws IllegalArgumentException {
        List<Infection> infections = infectionRepository.findCurrentByCountry(country);
        return infections.get(infections.size()-1);
    }

    @Override
    public Collection<Infection> getAllInfectionForContinent(Continent continent) {
        return infectionRepository.findByContinent(continent);
    }

    @Override
    public Collection<Infection> getCurrentInfectionByContinent(Continent continent) throws IllegalArgumentException {
        List<Country> countries = countryRepository.findByContinent(continent);
        Collection<Infection> infections = new ArrayList<Infection>();
        for(Country country: countries){
            List<Infection> country_infections = infectionRepository.findCurrentByCountry(country);
            infections.add(country_infections.get(country_infections.size()-1));
        }
        return infections;
    }

    @Override
    public Collection<Infection> getAllInfection() {
        return StreamSupport.stream(infectionRepository.findAll().spliterator(), false).
                collect(Collectors.toList());
    }

    @Override
    public Collection<Infection> addNewInfections() {
        Collection<Infection> infections = Scrapper.ReadCovid();
        Collection<Infection> newInfections = new ArrayList<Infection>();
        for(Infection inf : infections){
            Country country = null;
            if(countryRepository.getCountryByName(inf.getCountry().getName()).isEmpty()) {
                try {
                    country = countryRepository.save(inf.getCountry());
                }
                catch (DataAccessException e){
                    throw  new IllegalArgumentException(e);
                }
            }else{
                country = countryRepository.getCountryByName(inf.getCountry().getName()).get(0);
            }
            inf.setCountry(country);
            Infection newInfection = infectionRepository.save(inf);
            newInfections.add(newInfection);
        }
        return newInfections;
    }

    @Override
    public Infection addInfection(Infection infection) throws IllegalArgumentException {
        if(infection.getId() == null || !infectionRepository.existsById(infection.getId())) {
            try {
                return infectionRepository.save(infection);
            }
            catch (DataAccessException e){
                throw  new IllegalArgumentException(e);
            }
        }else
            throw new IllegalArgumentException("Table country already exist");
    }
}
