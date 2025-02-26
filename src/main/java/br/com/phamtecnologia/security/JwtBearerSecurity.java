package br.com.phamtecnologia.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtBearerSecurity {

	@Value("${jwt.secretkey}")
	String jwtSecretKey;
	
	@Value("${jwt.expiration}")
	String jwtExpiration;
	
	public Date getExpiration() {
		Date dataAtual = new Date();
		return new Date(dataAtual.getTime() + Integer.parseInt(jwtExpiration) * 1000);
	}
	
	
	public String getToken(String emailUsuario) {
		
		return Jwts.builder()
				.setSubject(emailUsuario)
				.setIssuedAt(new Date())
				.setExpiration(getExpiration())
				.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
				.compact();
	}
	
	
}
