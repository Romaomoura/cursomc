package com.romaomoura.cursospringmvc.services;

import java.util.List;

import com.romaomoura.cursospringmvc.domain.locale.Estado;
import com.romaomoura.cursospringmvc.repositories.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll(){
        return estadoRepository.findAllByOrderByNome();
    }
    
}