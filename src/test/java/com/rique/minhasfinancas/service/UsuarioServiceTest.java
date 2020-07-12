package com.rique.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rique.minhasfinancas.exception.ErroAutenticacao;
import com.rique.minhasfinancas.exception.RegraNegocioException;
import com.rique.minhasfinancas.model.entity.Usuario;
import com.rique.minhasfinancas.model.repository.UsuarioRepository;
import com.rique.minhasfinancas.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;

	@MockBean
	UsuarioRepository repository;

	// @BeforeEach
	// public void setUp() {
	// service = new UsuarioServiceImpl(repository);
	// }

	@Test
	public void deveSalvarUmUsuario() {
		// cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		// ação
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

		// verificação
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
	}

	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		// cenário
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

		// ação
		org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class,
				() -> service.salvarUsuario(usuario));

		// verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {

		// cenário
		String email = "email@email.com";
		String senha = "senha";

		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		// ação
		Usuario result = service.autenticar(email, senha);

		// verificação
		org.assertj.core.api.Assertions.assertThat(result).isNotNull();
	}

	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {

		// cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// service.autenticar("email@email.com", "senha");

		// ação

		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));

		// verificação
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class)
				.hasMessage("Usuário não encontrado para o e-mail informado.");

	}

	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {

		// cenário
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		// ação
		// service.autenticar("email@email.com", "123");

		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");

	}

	@Test
	public void deveValidarEmail() {

		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// ação/execução
		service.validarEmail("email@email.com");

	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

		// ação/execução
		// service.validarEmail("email@email.com");
		org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class,
				() -> service.validarEmail("email@email.com"));

	}

}
