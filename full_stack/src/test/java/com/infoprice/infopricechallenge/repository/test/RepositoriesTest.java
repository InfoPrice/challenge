package com.infoprice.infopricechallenge.repository.test;


import com.infoprice.infopricechallenge.entities.Address;
import com.infoprice.infopricechallenge.entities.City;
import com.infoprice.infopricechallenge.entities.EnumStates;
import com.infoprice.infopricechallenge.entities.Person;
import com.infoprice.infopricechallenge.repository.AddressRepository;
import com.infoprice.infopricechallenge.repository.CityRepository;
import com.infoprice.infopricechallenge.repository.PersonRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RepositoriesTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private Optional<Address> addressById;

    private City city = new City();
    private Person person = new Person();
    private Address address = new Address();
    private Set<Person> setPerson = new HashSet<>();
    private Set<Address> setAddress = new HashSet<>();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        setAddress.add(createAddress());
        setPerson.add(createPerson());
        city = createCity();
        person = createPerson();
        address = createAddress();

    }

    @Test
    public void testSaveAddress(){
        when(addressRepository.save(address)).thenReturn(address);
        Assert.notNull(addressRepository.save(address), "Its not null");
        Assert.isTrue(addressRepository.save(address).equals(address), "Test Passed");
    }

    @Test
    public void testFindAddress() {
        List resultAddress = new ArrayList();
        resultAddress.add(address);
        when(addressRepository.findAll()).thenReturn(resultAddress);
        Assert.notEmpty(addressRepository.findAll(), "Its not null");
        when(addressRepository.findById(anyString())).thenReturn(addressById);
        Assert.isTrue(addressRepository.findById("00001").equals(addressById), "Object not found");
    }

    private Person createPerson() {
        Person person = new Person();
        person.setEmail("thiago@gmail.com");
        person.setEnderecos(setAddress);
        return person;
    }

    private Address createAddress(){
        Address address = new Address();
        address.setBairro("Alto Branco");
        address.setCep("58104694");
        address.setCidade(city);
        address.setComplemento("");
        address.setEstado(EnumStates.PB);
        address.setLogradouro("Alfredo Farias Pimentel");
        address.setNumero("38");
        address.setPerson(setPerson);
        address.setPreferencial(true);
        return address;
    }

    private City createCity() {
        City city = new City();
        setAddress.add(address);
        city.setNome("Campina Grande");
        city.setEndereco(setAddress);
        return city;
    }

}
