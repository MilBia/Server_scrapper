package com.scrapper.country.repository;

import com.scrapper.country.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {
}
