package com.infoprice.infopricechallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infoprice.infopricechallenge.entities.City;
import com.infoprice.infopricechallenge.entities.EnumStates;
import com.infoprice.infopricechallenge.entities.Person;

import java.io.Serializable;
import java.util.Set;

public class AddressDTO implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("cep")
    private String cep;
    @JsonProperty("logradouro")
    private String logradouro;
    @JsonProperty("numero")
    private String numero;
    @JsonProperty("complemento")
    private String complemento;
    @JsonProperty("bairro")
    private String bairro;
    @JsonProperty("idCidade")
    private City cidade;
    @JsonProperty("estado")
    private EnumStates estado;
    @JsonProperty("preferencial")
    private Boolean preferencial;
    @JsonProperty("idPessoa")
    private Set<Person> person;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public City getCidade() {
        return cidade;
    }

    public void setCidade(City cidade) {
        this.cidade = cidade;
    }

    public EnumStates getEstado() {
        return estado;
    }

    public void setEstado(EnumStates estado) {
        this.estado = estado;
    }

    public Boolean getPreferencial() {
        return preferencial;
    }

    public void setPreferencial(Boolean preferencial) {
        this.preferencial = preferencial;
    }

    public Set<Person> getPerson() {
        return person;
    }

    public void setPerson(Set<Person> person) {
        this.person = person;
    }
}
