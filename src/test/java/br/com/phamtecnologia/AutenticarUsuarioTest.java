package br.com.phamtecnologia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.phamtecnologia.dtos.AutenticarUsuarioRequestDto;
import br.com.phamtecnologia.dtos.AutenticarUsuarioResponseDto;
import br.com.phamtecnologia.dtos.CriarUsuarioRequestDto;
import br.com.phamtecnologia.dtos.ErrorResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutenticarUsuarioTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	Faker faker = new Faker();

	@Order(1)
	@Test
	public void autenticarUsuarioComSucesso() throws Exception{

		CriarUsuarioRequestDto criarUsuarioDto = new CriarUsuarioRequestDto();
		criarUsuarioDto.setNome(faker.name().fullName());
		criarUsuarioDto.setEmail(faker.internet().emailAddress());
		criarUsuarioDto.setSenha("Root2024@");
		
		mockMvc.perform(post("/api/usuario/criar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(criarUsuarioDto)));
		
		AutenticarUsuarioRequestDto autenticarUsuarioDto = new AutenticarUsuarioRequestDto();
		autenticarUsuarioDto.setEmail(criarUsuarioDto.getEmail());
		autenticarUsuarioDto.setSenha(criarUsuarioDto.getSenha());
		
		MvcResult result =  mockMvc.perform(post("/api/usuario/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(criarUsuarioDto)))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		AutenticarUsuarioResponseDto response =  objectMapper.readValue(content, AutenticarUsuarioResponseDto.class);
		
		assertTrue(response.getAccessToken() != null); 
	}
	
	@Order(2)
	@Test
	public void acessoNegado() throws Exception{

		AutenticarUsuarioRequestDto autenticarUsuarioDto = new AutenticarUsuarioRequestDto();
		autenticarUsuarioDto.setEmail(faker.internet().emailAddress());
		autenticarUsuarioDto.setSenha("@Teste1234");
		
		MvcResult result =  mockMvc.perform(post("/api/usuario/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(autenticarUsuarioDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		ErrorResponseDto response =  objectMapper.readValue(content, ErrorResponseDto.class);
		
		assertEquals(new String(response.getErrors().get(0).getBytes(StandardCharsets.UTF_8)), 
				"Acesso negado. Usuário inválido.");
		
	}
	
	@Order(3)
	@Test
	public void validacaoDecampos() throws Exception{
		
		AutenticarUsuarioRequestDto autenticarUsuarioDto = new AutenticarUsuarioRequestDto();
		autenticarUsuarioDto.setEmail(null);
		autenticarUsuarioDto.setSenha(null);
		
		MvcResult result =  mockMvc.perform(post("/api/usuario/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(autenticarUsuarioDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		ErrorResponseDto response = objectMapper.readValue(content, ErrorResponseDto.class);
		
		assertTrue(response.getErrors().size() >= 2);
	}
	
}
