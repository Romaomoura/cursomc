package com.romaomoura.cursospringmvc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.romaomoura.cursospringmvc.domain.pagamento.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantePedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instantePedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(calendar.getTime());
	}
}
