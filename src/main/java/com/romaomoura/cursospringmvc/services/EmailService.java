package com.romaomoura.cursospringmvc.services;

import org.springframework.mail.SimpleMailMessage;

import com.romaomoura.cursospringmvc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmarionEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
