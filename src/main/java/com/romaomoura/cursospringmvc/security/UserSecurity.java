package com.romaomoura.cursospringmvc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.romaomoura.cursospringmvc.domain.enums.Perfil;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurity implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSecurity() {

    }

    public UserSecurity(Integer id, String email, String senha, Set<Perfil> perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getdescricao())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public boolean hasRole(Perfil perfil){
        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getdescricao()));
    }

}