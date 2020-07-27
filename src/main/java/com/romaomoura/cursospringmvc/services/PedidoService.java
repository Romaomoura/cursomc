package com.romaomoura.cursospringmvc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.romaomoura.cursospringmvc.domain.ItemPedido;
import com.romaomoura.cursospringmvc.domain.Pedido;
import com.romaomoura.cursospringmvc.domain.enums.EstadoPagamento;
import com.romaomoura.cursospringmvc.domain.pagamento.PagamentoComBoleto;
import com.romaomoura.cursospringmvc.exceptions.ObjectNotFoundException;
import com.romaomoura.cursospringmvc.repositories.ItemPedidoRepository;
import com.romaomoura.cursospringmvc.repositories.PagamentoRepository;
import com.romaomoura.cursospringmvc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repPedido;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagRepo;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService cliService;

	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repPedido.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(cliService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repPedido.save(obj);
		pagRepo.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItems()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItems());
		emailService.sendOrderConfirmationHtmlEmail(obj);

		return obj;
	}
}
