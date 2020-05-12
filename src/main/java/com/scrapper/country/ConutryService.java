package com.scrapper.country;

import com.scrapper.country.model.Country;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface ConutryService {

    Optional<Country> getCountryById(Integer countryId) throws IllegalArgumentException;

    Collection<Country> getAllCountries();

    Country addCountry(Country country) throws IllegalArgumentException;

    void deleteCountryById(Integer countryId)  throws IllegalArgumentException;
}
