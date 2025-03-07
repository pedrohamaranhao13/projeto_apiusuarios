package br.com.phamtecnologia;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutenticarUsuarioTest {

	@Order(1)
	@Test
	public void autenticarUsuarioComSucesso() {
		fail("Não implementado");
	}
	
	@Order(2)
	@Test
	public void acessoNegado() {
		fail("Não implementado");
	}
	
	@Order(3)
	@Test
	public void validacaoDecampos() {
		fail("Não implementado");
	}
	
}
