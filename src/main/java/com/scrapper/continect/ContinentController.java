package com.scrapper.continect;

import com.scrapper.auth.RoleType;
import com.scrapper.infections.InfectionService;
import com.scrapper.infections.model.Infection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping(value = "/continent")
public class ContinentController {

    @Autowired
    private InfectionService infectionService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Continent>> getContinents(){
        return new ResponseEntity<>( Arrays.asList(Continent.values()), HttpStatus.OK);
    }

    @RequestMapping(value = "infections/{continent_name}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Infection>> getCountryInfections(@PathVariable String continent_name, @CookieValue(value = "user", defaultValue = "") String userCredential){
        if(userCredential.equals(RoleType.ADMIN.toString())) {
            return new ResponseEntity<>(infectionService.getAllInfectionForContinent(Continent.valueOf(continent_name.toUpperCase())), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "infections_current/{continent_name}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Infection>> getCurrentContinentInfections(@PathVariable String continent_name){
        return new ResponseEntity<>(infectionService.getCurrentInfectionByContinent(Continent.valueOf(continent_name.toUpperCase())), HttpStatus.OK);
    }
}
