package com.romaomoura.cursospringmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.romaomoura.cursospringmvc.domain.Cliente;
import com.romaomoura.cursospringmvc.domain.Endereco;
import com.romaomoura.cursospringmvc.domain.enums.TipoCliente;
import com.romaomoura.cursospringmvc.domain.locale.Cidade;
import com.romaomoura.cursospringmvc.dto.ClienteDTO;
import com.romaomoura.cursospringmvc.dto.ClienteNewDTO;
import com.romaomoura.cursospringmvc.exceptions.DataIntegratyException;
import com.romaomoura.cursospringmvc.exceptions.ObjectNotFoundException;
import com.romaomoura.cursospringmvc.repositories.ClienteRepository;
import com.romaomoura.cursospringmvc.repositories.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repCliente;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	private EnderecoRepository endRepository;

	// @Autowired
	// private CidadeRepository cidadeRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repCliente.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repCliente.save(obj);
		endRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegratyException("Não é possível excluir um cliente que possua pedidos relacionados.");
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipoCliente()), passEncoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
