package com.infoprice.infopricechallenge.service;

import com.infoprice.infopricechallenge.dto.ViaCepDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ViaCepService {

    Map<String, Object> findAddressByCep(final String cep);

}
