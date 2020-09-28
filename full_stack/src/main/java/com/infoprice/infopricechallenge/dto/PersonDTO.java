package com.infoprice.infopricechallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infoprice.infopricechallenge.entities.Address;

import java.io.Serializable;
import java.util.Set;

public class PersonDTO implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("telefone")
    private String telefone;
    @JsonProperty("idEnderecos")
    private Set<Address> enderecos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Address> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Address> enderecos) {
        this.enderecos = enderecos;
    }
}
