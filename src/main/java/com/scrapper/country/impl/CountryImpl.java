package com.scrapper.country.impl;

import com.scrapper.country.ConutryService;
import com.scrapper.country.model.Country;
import com.scrapper.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CountryImpl implements ConutryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Optional<Country> getCountryById(Integer countryId) throws IllegalArgumentException {
        return countryRepository.findById(countryId);
    }

    @Override
    public Collection<Country> getAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).
                collect(Collectors.toList());
    }

    @Override
    public Country addCountry(Country country) throws IllegalArgumentException {
        if(country.getId() == null || !countryRepository.existsById(country.getId())) {
            try {
                return countryRepository.save(country);
            }
            catch (DataAccessException e){
                throw  new IllegalArgumentException(e);
            }
        }else
            throw new IllegalArgumentException("Table country already exist");
    }

    @Override
    public void deleteCountryById(Integer countryId) throws IllegalArgumentException {
        if(countryRepository.existsById(countryId)) {
            try {
                countryRepository.deleteById(countryId);
            }
            catch (DataAccessException e){
                throw  new IllegalArgumentException(e);
            }
        }else
            throw new IllegalArgumentException("Table country already exist");
    }
}
