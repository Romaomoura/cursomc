package com.romaomoura.cursospringmvc.dto;

import java.io.Serializable;

public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    public CredenciaisDTO() {
    }

    /*
     * public CredenciaisDTO(Cliente obj) { email = obj.getEmail(); senha =
     * obj.getSenha(); }
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}