package com.romaomoura.cursospringmvc.dto;

import java.io.Serializable;

import com.romaomoura.cursospringmvc.domain.locale.Estado;

public class EstadoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDto(){

    }
    
    public EstadoDto(Estado obj){
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