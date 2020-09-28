package com.infoprice.infopricechallenge.populators.impl;

import com.infoprice.infopricechallenge.dto.PersonDTO;
import com.infoprice.infopricechallenge.entities.Person;
import com.infoprice.infopricechallenge.populators.PersonPopulator;
import org.springframework.stereotype.Component;

@Component
public class DefaultPersonPopulator implements PersonPopulator {
    @Override
    public PersonDTO populate(Person source, PersonDTO target) {
        if (source.getId() != null) {
            target.setId(source.getId().toString());
        }
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
        if (source.getEnderecos() != null) {
            target.setEnderecos(source.getEnderecos());
        }
        if (source.getNome() != null){
            target.setName(source.getNome());
        }
        if (source.getTelefone() != null) {
            target.setTelefone(source.getTelefone());
        }
        return target;
    }
}
