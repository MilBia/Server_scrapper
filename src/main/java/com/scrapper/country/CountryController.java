package com.scrapper.country;

import com.scrapper.country.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/countries")
public class CountryController {

    @Autowired
    private ConutryService countryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Country> getCountries(){
        return countryService.getAllCountries();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Country> getCountry(Integer id){
        return countryService.getCountryById(id).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).
                orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Country> addCountry(@RequestBody Country country){
        try {
            return new ResponseEntity<>(countryService.addCountry(country), HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Country> deleteCountry(Integer id){
        try {
            countryService.deleteCountryById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
