package com.maykon.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.maykon.cursomc.domain.Cliente;
import com.maykon.cursomc.domain.Pedido;

public interface EmailService {

	void envioPedidoConfirmado(Pedido obj);
		
	void envioEmail(SimpleMailMessage msg);
	
	
	// Email formato HTML
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void envioHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
	
}
