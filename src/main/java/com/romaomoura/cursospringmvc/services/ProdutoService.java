package com.romaomoura.cursospringmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.domain.Produto;
import com.romaomoura.cursospringmvc.services.exceptions.ObjectNotFoundException;
import com.romaomoura.cursospringmvc.repositories.CategoriaRepository;
import com.romaomoura.cursospringmvc.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repProduto;
	
	@Autowired
	private CategoriaRepository repCategoria;

	public Produto find(Integer id) {
		Optional<Produto> obj = repProduto.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String ordeBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), ordeBy);
		
		List<Categoria> categorias = repCategoria.findAllById(ids);
		
		return repProduto.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}
}
