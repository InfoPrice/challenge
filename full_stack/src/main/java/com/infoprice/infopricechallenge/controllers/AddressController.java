package com.infoprice.infopricechallenge.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoprice.infopricechallenge.dto.AddressDTO;
import com.infoprice.infopricechallenge.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/infoprice")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/{id}/address", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object>getAddressById(@PathVariable("id") String id) {
        return addressService.retrieveAddress(id);
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String>addAddress(@RequestBody final AddressDTO addressDTO){
        return addressService.saveAddress(addressDTO);
    }

    @RequestMapping(value = "/{id}/address", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, String>editAddress (@PathVariable("id")final String id, @RequestBody final AddressDTO addressDTO ){
        addressDTO.setId(id);
        return addressService.editAddress(addressDTO);
    }

    @RequestMapping(value = "/{id}/address", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String>deleteAddress(@PathVariable("id")final String id){
        return addressService.deleteAddress(id);
    }


}
