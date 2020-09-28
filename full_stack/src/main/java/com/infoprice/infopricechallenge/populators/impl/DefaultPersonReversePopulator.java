package com.infoprice.infopricechallenge.populators.impl;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import com.infoprice.infopricechallenge.entities.Person;
import com.infoprice.infopricechallenge.populators.PersonReversePopulator;
import org.springframework.stereotype.Component;

@Component
public class DefaultPersonReversePopulator implements PersonReversePopulator {

    @Override
    public Person populate(PersonDTO source, Person target) {
        if (source.getId() != null) {
            target.setId(Long.parseLong(source.getId()));
        }
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
        if (source.getEnderecos() != null) {
            target.setEnderecos(source.getEnderecos());
        }
        if (source.getName() != null){
            target.setNome(source.getName());
        }
        if (source.getTelefone() != null) {
            target.setTelefone(source.getTelefone());
        }
        return target;
    }
}
