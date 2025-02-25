package br.com.phamtecnologia.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phamtecnologia.dtos.AutenticarUsuarioRequestDto;
import br.com.phamtecnologia.dtos.AutenticarUsuarioResponseDto;
import br.com.phamtecnologia.dtos.CriarUsuarioRequestDto;
import br.com.phamtecnologia.dtos.CriarUsuarioResponseDto;
import br.com.phamtecnologia.entities.Usuario;
import br.com.phamtecnologia.helpers.Sha1CryptoHelper;
import br.com.phamtecnologia.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) {

		if(usuarioRepository.find(request.getEmail()) != null) {
			throw new IllegalArgumentException("Já existe um usuário cadastrado com o e-mail informado.");
		}
		
		Usuario usuario = new Usuario();
		usuario.setId(UUID.randomUUID());
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(Sha1CryptoHelper.get(request.getSenha()));
		
		usuarioRepository.save(usuario);
		
		CriarUsuarioResponseDto response = new CriarUsuarioResponseDto();
		response.setIdUsuario(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setDataHoraCriacao(Instant.now());
		return response;
	}
	
	public AutenticarUsuarioResponseDto autenticarUsuario(AutenticarUsuarioRequestDto request) {
		// TODO
		return null;
	}
}
