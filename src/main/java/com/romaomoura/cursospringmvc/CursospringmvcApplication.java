package com.romaomoura.cursospringmvc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.domain.Cliente;
import com.romaomoura.cursospringmvc.domain.Endereco;
import com.romaomoura.cursospringmvc.domain.Produto;
import com.romaomoura.cursospringmvc.domain.enums.TipoCliente;
import com.romaomoura.cursospringmvc.domain.locale.Cidade;
import com.romaomoura.cursospringmvc.domain.locale.Estado;
import com.romaomoura.cursospringmvc.repositories.CategoriaRepository;
import com.romaomoura.cursospringmvc.repositories.CidadeRepository;
import com.romaomoura.cursospringmvc.repositories.ClienteRepository;
import com.romaomoura.cursospringmvc.repositories.EnderecoRepository;
import com.romaomoura.cursospringmvc.repositories.EstadoRepository;
import com.romaomoura.cursospringmvc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursospringmvcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursospringmvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Betim", est1);
		Cidade c2 = new Cidade(null, "Carapicuiba", est2);
		Cidade c3 = new Cidade(null, "Atibaia", est2);
		Cidade c4 = new Cidade(null, "Belo Horizonte", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c4));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

		Cliente cli1 = new Cliente(null, "Maria silva", "maria@gmail.com", "11.254.3654/0001-65", TipoCliente.PESSOAJURIDICA);
		cli1.getTelefones().addAll(Arrays.asList("(88) 965324852", "(88) 965328974"));
		
		Endereco end1 = new Endereco(null, "Rua Todos os santos", "1112", "Apartamento", "Pirajá", "63.066-222", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Tiradentes", "055", "Casa", "Santista", "64.096-222", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
	}

}
