package com.infoprice.infopricechallenge.service;

import com.infoprice.infopricechallenge.dto.AddressDTO;

import java.util.Map;

public interface AddressService {

    Map<String, Object> retrieveAddress(final String id);
    Map<String,String> saveAddress(final AddressDTO addressDTO);
    Map<String,String> deleteAddress(final String id);
    Map<String, String> editAddress(final AddressDTO addressDTO);

}
