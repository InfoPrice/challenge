package com.infoprice.infopricechallenge.service.impl;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import com.infoprice.infopricechallenge.entities.Person;
import com.infoprice.infopricechallenge.populators.PersonPopulator;
import com.infoprice.infopricechallenge.populators.PersonReversePopulator;
import com.infoprice.infopricechallenge.repository.PersonRepository;
import com.infoprice.infopricechallenge.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DefaultPersonService implements PersonService {

    final Logger logger = LoggerFactory.getLogger(DefaultPersonService.class);

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonPopulator personPopulator;
    @Autowired
    PersonReversePopulator personReversePopulator;

    @Override
    public Map<String, Object> retrievePerson(String id) {
        Map<String, Object> result = new HashMap<>();
        Optional<Person> newPerson;
        Long newID;
        if (id != null) {
            try {
                newID = Long.parseLong(id);
                newPerson = personRepository.findById(newID);
                if (newPerson == null || !newPerson.isPresent()){
                    logger.info("Person's ID not found in DB");
                    result.put("operationStatus", "fail");
                    return result;
                }
                if (newPerson!= null && newPerson.isPresent()){
                    PersonDTO person = personPopulator.populate(newPerson.get(), new PersonDTO());
                    result.put("operationStatus", "success");
                    result.put("data", person);
                    logger.info("Persons's ID " + id + " found, returning the required object");
                    return result;
                }
            } catch (Exception e) {
                logger.error("Error while trying to retrieve person from retrievePerson: "+e.getMessage(), e);
                result.put("operationStatus", "fail");
                return result;
            }

        }
        logger.error("Person ID cannot be null in retrievePerson");
        result.put("operationStatus", "fail");
        return result;
    }

    @Override
    public Map<String, String> savePerson(PersonDTO personDTO) {
        Map<String, String> result = new HashMap<>();
        Person newPerson;
        if (personDTO == null) {
            logger.error("Object PersonDTO is null of savePerson.");
            result.put("operationStatus", "fail");
            return result;
        }
        try {
            newPerson = personReversePopulator.populate(personDTO, new Person());
            personRepository.save(newPerson);
            result.put("operationStatus", "success");
            return result;
        } catch (Exception e) {
            logger.error("An erro occurred during savePerson: "+e.getMessage(), e);
            result.put("statusOperation", "fail");
            return result;
        }

    }

    @Override
    public Map<String, String> deletePerson(String id) {
        Map<String, String> result = new HashMap<>();
        Long newID = Long.parseLong(id);
        if (id == null) {
            logger.error("Attribute of deletePerson cannot be null");
            result.put("operationStatus", "fail");
            return result;
        }
        try {
            if (personRepository.existsById(newID)){
                personRepository.deleteById(newID);
                result.put("operationStatus", "success");
                logger.info("Person "+ id + " successfully deleted");
                return result;
            }
            result.put("operationStatus", "fail");
            logger.info("Address's ID not found in DB");
            return result;
        } catch (Exception e) {
            logger.error("An error occurred during deletePerson "+ e.getMessage(), e);
            result.put("operationStatus", "fail");
            return result;
        }
    }
}
