package com.infoprice.infopricechallenge.repository;

import com.infoprice.infopricechallenge.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findAll();
    Optional<Address> findById(final Long id);
    void deleteById(final Long id);
    boolean existsById(final Long id);
    Address save(final Address address);

}
