package com.romaomoura.cursospringmvc.services;

import com.romaomoura.cursospringmvc.security.UserSecurity;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSecurity authenticated() {
        try {

            return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

}