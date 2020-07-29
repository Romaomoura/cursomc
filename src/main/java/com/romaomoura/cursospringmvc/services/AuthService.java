package com.romaomoura.cursospringmvc.services;

import java.util.Random;

import com.romaomoura.cursospringmvc.domain.Cliente;
import com.romaomoura.cursospringmvc.repositories.ClienteRepository;
import com.romaomoura.cursospringmvc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository cliRepo;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = cliRepo.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado!");
        }
        String newPass = newPassword();
        cliente.setSenha(passEncoder.encode(newPass));

        cliRepo.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vetor = new char[10];
        for (int i = 0; i < 10; i++) {
            vetor[i] = randomChar();
        }
        return new String(vetor);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) { // gera letra maiuscula
            return (char) (random.nextInt(26) + 65);
        } else {// gera letra minuscula
            return (char) (random.nextInt(26) + 97);
        }
    }

}