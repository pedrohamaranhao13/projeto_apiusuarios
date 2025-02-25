package br.com.phamtecnologia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phamtecnologia.dtos.AutenticarUsuarioRequestDto;
import br.com.phamtecnologia.dtos.AutenticarUsuarioResponseDto;
import br.com.phamtecnologia.dtos.CriarUsuarioRequestDto;
import br.com.phamtecnologia.dtos.CriarUsuarioResponseDto;
import br.com.phamtecnologia.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping("criar")
	public CriarUsuarioResponseDto criarUsuario(@RequestBody @Valid CriarUsuarioRequestDto request) {
		return usuarioService.criarUsuario(request);
	}
	
	@PostMapping("autenticar")
	public AutenticarUsuarioResponseDto autenticarUsuario(@RequestBody @Valid AutenticarUsuarioRequestDto request) {
		return usuarioService.autenticarUsuario(request);
	}
	

}
