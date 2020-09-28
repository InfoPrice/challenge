package com.infoprice.infopricechallenge.service;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface PersonService {

    Map<String, Object> retrievePerson(final String id);
    Map<String, String> savePerson(final PersonDTO personDTO);
    Map<String, String> deletePerson(final String id);

}
