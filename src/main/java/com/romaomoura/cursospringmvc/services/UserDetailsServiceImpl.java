package com.romaomoura.cursospringmvc.services;

import com.romaomoura.cursospringmvc.domain.Cliente;
import com.romaomoura.cursospringmvc.repositories.ClienteRepository;
import com.romaomoura.cursospringmvc.security.UserSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository cliRep;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cli = cliRep.findByEmail(email);
        if (cli == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
    }

}