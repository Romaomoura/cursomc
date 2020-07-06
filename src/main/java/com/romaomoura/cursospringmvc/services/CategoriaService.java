package com.romaomoura.cursospringmvc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repCategoria;

	public Optional<Categoria> buscar(Integer id) {
		Optional<Categoria> obj = repCategoria.findById(id);
		return obj;
	}
}
