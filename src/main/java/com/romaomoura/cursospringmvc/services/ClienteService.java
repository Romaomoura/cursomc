package com.romaomoura.cursospringmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.Cliente;
import com.romaomoura.cursospringmvc.dto.ClienteDTO;
import com.romaomoura.cursospringmvc.exceptions.DataIntegratyException;
import com.romaomoura.cursospringmvc.exceptions.ObjectNotFoundException;
import com.romaomoura.cursospringmvc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repCliente;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repCliente.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repCliente.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repCliente.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegratyException("Não é possível excluir um cliente que possua pedidos.");
		}
	}

	public List<Cliente> findAll() {
		return repCliente.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String ordeBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), ordeBy);
		return repCliente.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
