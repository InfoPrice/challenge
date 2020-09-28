package com.infoprice.infopricechallenge.controllers;

import com.infoprice.infopricechallenge.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/infoprice/viacep")
public class ViaCepController {

    @Autowired
    ViaCepService viaCepService;

    @RequestMapping(value = "/{cep}/find", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAddressByCep(@PathVariable("cep") final String cep){
        return viaCepService.findAddressByCep(cep);
    }

}
