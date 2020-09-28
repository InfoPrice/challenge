package com.infoprice.infopricechallenge.service.impl;

import com.infoprice.infopricechallenge.dto.AddressDTO;
import com.infoprice.infopricechallenge.entities.Address;
import com.infoprice.infopricechallenge.populators.AddressPopulator;
import com.infoprice.infopricechallenge.populators.AddressReversePopulator;
import com.infoprice.infopricechallenge.repository.AddressRepository;
import com.infoprice.infopricechallenge.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultAddressService implements AddressService {

    final Logger logger = LoggerFactory.getLogger(DefaultAddressService.class);

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressPopulator addressPopulator;
    @Autowired
    AddressReversePopulator addressReversePopulator;
    private AddressDTO dto;
    private Address entity;

    @Override
    public final Map<String, Object> retrieveAddress(final String id){
        Map<String,Object> result = new HashMap<>();
        AddressDTO addressDTO;
        Optional<Address> retrievedAddress;
        Long newID = Long.parseLong(id);
        if (id != null) {
            try {
                retrievedAddress = addressRepository.findById(newID);

                if (retrievedAddress == null) {
                    logger.info("Address's ID not found in DB");
                    result.put("operationStatus", "fail");
                    return result;
                }
                if (retrievedAddress != null && retrievedAddress.isPresent()) {
                    logger.info("Address's ID " + id + " found, returning the required object");
                    addressDTO = addressPopulator.populate(retrievedAddress.get(), new AddressDTO());
                    result.put("operationStatus", "success");
                    result.put("data", addressDTO);
                    return result;
                }
            } catch (Exception e) {
                logger.error("An error occurred when trying to retrieve an address: " + e.getMessage(), e);
                result.put("operationStatus", "fail");
                return result;
            }
        }
        logger.error("Attribute ID of retrieveAddress cannot be null");
        result.put("operationStatus", "fail");
        return result;
    }
    @Override
    public Map<String,String> saveAddress(final AddressDTO addressDTO){
        Address newAddress;
        Map<String, String> result = new HashMap<>();
        if (addressDTO == null) {
            logger.error("Object AddressDTO is null of saveAddress.");
            result.put("operationStatus", "fail");
            return result;
        }
        try {
            //Can refactor this block of code as a method
            newAddress = addressReversePopulator.populate(addressDTO,  new Address());
            addressRepository.save(newAddress);
            result.put("operationStatus", "success");
            return result;
        } catch (Exception e) {
            result.put("operationStatus", "fail");
            logger.error("Error while trying to save new address: " + e.getMessage(), e);
            return result;
        }
    }
    @Override
    public Map<String,String> deleteAddress(final String id) {
        Map<String,String> result = new HashMap<>();
        final Long newID = Long.parseLong(id);
        if (id == null){
            logger.error("Attribute ID of deleteAddress is null.");
            result.put("operationStatus", "fail");
            return result;
        }
        if (addressRepository.existsById(newID)){
            addressRepository.deleteById(newID);
            logger.info("Address "+ id + " successfully deleted");
            result.put("operationStatus", "success");
            return result;
        }
        result.put("operationStatus", "fail");
        logger.info("Address's ID not found in DB");
        return result;
    }
    @Override
    public Map<String, String> editAddress(final AddressDTO addressDTO) {
        Map<String, String> result = new HashMap<>();
        Address newAddress;
        if (addressDTO == null || addressDTO.getId() == null) {
            logger.error("Object addressDto or Attribute ID of editAddress is null.");
            result.put("operationStatus", "fail");
            return result;
        }
        if (addressRepository.existsById(Long.parseLong(addressDTO.getId()))){
            try {
                //Can refactor this block of code as a method
                newAddress = addressReversePopulator.populate(addressDTO,  new Address());
                addressRepository.save(newAddress);
                result.put("operationStatus", "success");
                return result;
            } catch (Exception e) {
                result.put("operationStatus", "fail");
                logger.error("Error while trying to edit the address: " + e.getMessage(), e);
                return result;
            }
        }
        result.put("operationStatus","fail");
        logger.info("Address's id not found in DB. Not adding new one.");
        return result;
    }

}
