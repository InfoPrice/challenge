package com.infoprice.infopricechallenge.populators;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import com.infoprice.infopricechallenge.entities.Person;
import org.springframework.stereotype.Component;

@Component
public interface PersonReversePopulator {

    Person populate(PersonDTO source, Person target);

}
