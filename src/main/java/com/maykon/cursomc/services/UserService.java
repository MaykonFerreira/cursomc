package com.maykon.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.maykon.cursomc.security.UserSS;

public class UserService {
	
	
	//  Pega o usuário logado
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
