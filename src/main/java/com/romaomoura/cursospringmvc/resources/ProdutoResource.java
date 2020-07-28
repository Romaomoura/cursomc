package com.romaomoura.cursospringmvc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romaomoura.cursospringmvc.domain.Produto;
import com.romaomoura.cursospringmvc.dto.ProdutoDTO;
import com.romaomoura.cursospringmvc.resources.utils.Url;
import com.romaomoura.cursospringmvc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoServ;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = produtoServ.find(id);
		return ResponseEntity.ok().body(obj);
	}

	// Paginação para listar produtos
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "ordeBy", defaultValue = "nome") String ordeBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecode = Url.decodeParam(nome);
		List<Integer> ids = Url.decodeIntList(categorias);
		Page<Produto> produtos = produtoServ.search(nomeDecode, ids, page, linesPerPage, ordeBy, direction);
		Page<ProdutoDTO> produtosDTO = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(produtosDTO);
	}
}
