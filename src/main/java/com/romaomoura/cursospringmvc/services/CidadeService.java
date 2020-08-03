package com.romaomoura.cursospringmvc.services;

import java.util.List;

import com.romaomoura.cursospringmvc.domain.locale.Cidade;
import com.romaomoura.cursospringmvc.repositories.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}