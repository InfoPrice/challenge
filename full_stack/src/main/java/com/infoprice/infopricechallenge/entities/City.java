package com.infoprice.infopricechallenge.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class City implements Serializable {

    @Id
    private String id;
    private String nome;
    @OneToMany
    private Set<Address> endereco;

    public City() {

    }

    public City(String id, String nome, Set<Address> endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Address> getEndereco() {
        return endereco;
    }

    public void setEndereco(Set<Address> endereco) {
        this.endereco = endereco;
    }
}
