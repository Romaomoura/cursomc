package com.romaomoura.cursospringmvc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.exceptions.ObjectNotFoundException;
import com.romaomoura.cursospringmvc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repCategoria;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repCategoria.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repCategoria.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repCategoria.save(obj);
	}
}
