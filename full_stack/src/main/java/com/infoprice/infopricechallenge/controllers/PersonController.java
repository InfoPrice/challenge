package com.infoprice.infopricechallenge.controllers;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import com.infoprice.infopricechallenge.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/infoprice")
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping(value = "/{id}/person",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPersonById(@PathVariable("id") final String id){
        return personService.retrievePerson(id);
    }

    @RequestMapping(value = "/{id}/person", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, String> editPerson(@PathVariable("id") final String id, @RequestBody final PersonDTO newPerson){
        newPerson.setId(id);
         return personService.savePerson(newPerson);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> savePerson(@RequestBody final PersonDTO newPerson){
        return personService.savePerson(newPerson);
    }

    @RequestMapping(value = "/{id}/person", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> delePerson(@PathVariable ("id") final String id){
        return personService.deletePerson(id);
    }


}
