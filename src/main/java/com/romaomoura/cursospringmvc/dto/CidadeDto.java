package com.romaomoura.cursospringmvc.dto;

import java.io.Serializable;

import com.romaomoura.cursospringmvc.domain.locale.Cidade;

public class CidadeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CidadeDto() {
    }

    public CidadeDto(Cidade obj) {
        id = obj.getId();
        nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}