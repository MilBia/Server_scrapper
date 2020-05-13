package com.scrapper.infections.repository;

import com.scrapper.infections.model.Infection;
import org.springframework.data.repository.CrudRepository;

public interface InfectionRepository extends CrudRepository<Infection, Integer> {
}
