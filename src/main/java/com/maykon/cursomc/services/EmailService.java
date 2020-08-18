package com.maykon.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.maykon.cursomc.domain.Pedido;

public interface EmailService {

	void envioPedidoConfirmado(Pedido obj);
		
	void envioEmail(SimpleMailMessage msg);
	
	
}
