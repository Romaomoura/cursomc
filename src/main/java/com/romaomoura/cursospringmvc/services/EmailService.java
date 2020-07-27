package com.romaomoura.cursospringmvc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.romaomoura.cursospringmvc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmarionEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);//email com formatação simples

	void sendOrderConfirmationHtmlEmail(Pedido obj);

	void sendHtmlEmail(MimeMessage msg);//email com formatação html
}
