package com.infoprice.infopricechallenge.repository;

import com.infoprice.infopricechallenge.entities.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String> {

    List<Person> findAll();
    Optional<Person>findById(final Long id);
    void deleteById(final Long id);
    boolean existsById(final Long id);
    Person save(final Person newPerson);

}
