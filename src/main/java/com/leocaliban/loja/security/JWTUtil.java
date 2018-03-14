package com.leocaliban.loja.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Gera o token para o usuário
	 * @param email de login 
	 * @return token
	 */
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email) // login com email
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // expiração é o tempo atual + o tempo de expiração do properties
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
