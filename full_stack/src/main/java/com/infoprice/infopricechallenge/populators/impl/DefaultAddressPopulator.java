package com.infoprice.infopricechallenge.populators.impl;

import com.infoprice.infopricechallenge.dto.AddressDTO;
import com.infoprice.infopricechallenge.entities.Address;
import com.infoprice.infopricechallenge.populators.AddressPopulator;
import org.springframework.stereotype.Component;

@Component
public class DefaultAddressPopulator implements AddressPopulator {

    @Override
    public AddressDTO populate(final Address source, final AddressDTO target){

            if (source.getCep() != null) {
                target.setCep(source.getCep());
            }
            if (source.getBairro() != null) {
                target.setBairro(source.getBairro());
            }
            if (source.getCidade() != null) {
                target.setCidade(source.getCidade());
            }
            if (source.getComplemento() != null) {
                target.setComplemento(source.getComplemento());
            }
            if (source.getEstado() != null) {
                target.setEstado(source.getEstado());
            }
            if (source.getId() != null) {
                target.setId(source.getId().toString());
            }
            if (source.getLogradouro() != null) {
                target.setLogradouro(source.getLogradouro());
            }
            if (source.getNumero() != null) {
                target.setNumero(source.getNumero());
            }
            if (source.getPerson() != null) {
                target.setPerson(source.getPerson());
            }
            if (source.getPreferencial() != null) {
                target.setPreferencial(source.getPreferencial());
            }
            return target;
    }

}
