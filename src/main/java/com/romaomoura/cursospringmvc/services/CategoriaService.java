package com.romaomoura.cursospringmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.dto.CategoriaDTO;
import com.romaomoura.cursospringmvc.repositories.CategoriaRepository;
import com.romaomoura.cursospringmvc.services.exceptions.DataIntegratyException;
import com.romaomoura.cursospringmvc.services.exceptions.ObjectNotFoundException;

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
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repCategoria.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repCategoria.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegratyException("Não é possível excluir uma categoria que possui produtos.");
		}
	}

	public List<Categoria> findAll() {
		return repCategoria.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String ordeBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), ordeBy);
		return repCategoria.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
