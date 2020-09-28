package com.infoprice.infopricechallenge.populators;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import com.infoprice.infopricechallenge.entities.Person;
import org.springframework.stereotype.Component;

@Component
public interface PersonPopulator {

    PersonDTO populate(Person source, PersonDTO target);

}
