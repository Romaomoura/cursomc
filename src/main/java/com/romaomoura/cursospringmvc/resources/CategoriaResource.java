package com.romaomoura.cursospringmvc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.dto.CategoriaDTO;
import com.romaomoura.cursospringmvc.services.CategoriaService;

@RestController
@CrossOrigin
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService catServ;

	@ApiOperation(value = "Busca uma categoria")
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = catServ.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Insere uma categoria")
	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = catServ.fromDTO(objDto);
		obj = catServ.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Atualiza categoria")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @PathVariable Integer id, @RequestBody CategoriaDTO objDto) {
		Categoria obj = catServ.fromDTO(objDto);
		obj.setId(id);
		obj = catServ.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Deleta categoria") //teste de personalização de metodo para documentação
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		catServ.delete(id);
		return ResponseEntity.noContent().build();
	}

	// listando todas as categorias
	@ApiOperation(value = "Deleta todas as categoria")
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = catServ.findAll();
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}

	// Paginação para listar categorias
	@ApiOperation(value = "Deleta todas as categoria com paginação")
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "ordeBy", defaultValue = "nome") String ordeBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Categoria> categorias = catServ.findPage(page, linesPerPage, ordeBy, direction);
		Page<CategoriaDTO> categoriasDTO = categorias.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(categoriasDTO);
	}

}
