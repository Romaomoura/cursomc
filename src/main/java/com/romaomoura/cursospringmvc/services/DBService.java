package com.romaomoura.cursospringmvc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.Categoria;
import com.romaomoura.cursospringmvc.domain.Cliente;
import com.romaomoura.cursospringmvc.domain.Endereco;
import com.romaomoura.cursospringmvc.domain.ItemPedido;
import com.romaomoura.cursospringmvc.domain.Pedido;
import com.romaomoura.cursospringmvc.domain.Produto;
import com.romaomoura.cursospringmvc.domain.enums.EstadoPagamento;
import com.romaomoura.cursospringmvc.domain.enums.Perfil;
import com.romaomoura.cursospringmvc.domain.enums.TipoCliente;
import com.romaomoura.cursospringmvc.domain.locale.Cidade;
import com.romaomoura.cursospringmvc.domain.locale.Estado;
import com.romaomoura.cursospringmvc.domain.pagamento.Pagamento;
import com.romaomoura.cursospringmvc.domain.pagamento.PagamentoComBoleto;
import com.romaomoura.cursospringmvc.domain.pagamento.PagamentoComCartao;
import com.romaomoura.cursospringmvc.repositories.CategoriaRepository;
import com.romaomoura.cursospringmvc.repositories.CidadeRepository;
import com.romaomoura.cursospringmvc.repositories.ClienteRepository;
import com.romaomoura.cursospringmvc.repositories.EnderecoRepository;
import com.romaomoura.cursospringmvc.repositories.EstadoRepository;
import com.romaomoura.cursospringmvc.repositories.ItemPedidoRepository;
import com.romaomoura.cursospringmvc.repositories.PagamentoRepository;
import com.romaomoura.cursospringmvc.repositories.PedidoRepository;
import com.romaomoura.cursospringmvc.repositories.ProdutoRepository;

@Service
public class DBService {

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

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public void instatieteTestDatabase() throws ParseException {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Artigos para para o lar");
		Categoria cat6 = new Categoria(null, "Cozinha");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 80.00);
		Produto p5 = new Produto(null, "Toalha", 80.00);
		Produto p6 = new Produto(null, "Colcha de cama", 80.00);
		Produto p7 = new Produto(null, "Conjunto de Panelas Inox", 80.00);
		Produto p8 = new Produto(null, "Smart Tv", 80.00);
		Produto p9 = new Produto(null, "Home Teacher", 80.00);
		Produto p10 = new Produto(null, "Notebook", 80.00);
		Produto p11 = new Produto(null, "Geladeira", 80.00);
		Produto p12 = new Produto(null, "Abajour", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p10));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p8, p9));
		cat5.getProdutos().addAll(Arrays.asList(p12));
		cat6.getProdutos().addAll(Arrays.asList(p7, p11));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat3));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat6));
		p8.getCategorias().addAll(Arrays.asList(cat4));
		p9.getCategorias().addAll(Arrays.asList(cat4));
		p10.getCategorias().addAll(Arrays.asList(cat1));
		p11.getCategorias().addAll(Arrays.asList(cat6));
		p12.getCategorias().addAll(Arrays.asList(cat5));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));

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

		Cliente cli1 = new Cliente(null, "Maria silva", "c.romaomoura@gmail.com", "11.254.3654/0001-65",
				TipoCliente.PESSOAJURIDICA, passEncoder.encode("1234"));
		cli1.getTelefones().addAll(Arrays.asList("(88) 965324852", "(88) 965328974"));

		Cliente cli2 = new Cliente(null, "Romão Moura silva", "c.r.moura@hotmail.com", "088.658.123-65",
				TipoCliente.PESSOAFISICA, passEncoder.encode("12345"));
		cli2.getTelefones().addAll(Arrays.asList("(88) 965324858", "(88) 965328912"));
		cli2.addPerfil(Perfil.ADMIN);

		Endereco end1 = new Endereco(null, "Rua Todos os santos", "1112", "Apartamento", "Pirajá", "63.066-222", cli1,
				c1);
		Endereco end2 = new Endereco(null, "Rua Tiradentes", "055", "Casa", "Santista", "64.096-222", cli1, c2);
		
		Endereco end3 = new Endereco(null, "Rua da orquidias", "112", "Condominio", "Campo Alegre", "63.060-000", cli2,
				c3);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		cli2.getEnderecos().addAll(Arrays.asList(end3));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("30/11/2018 14:32"), cli1, end2);

		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);

		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.0);

		ped1.getItems().addAll(Arrays.asList(ip1, ip2));
		ped2.getItems().addAll(Arrays.asList(ip3));

		p1.getItems().addAll(Arrays.asList(ip1));
		p2.getItems().addAll(Arrays.asList(ip3));
		p3.getItems().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
