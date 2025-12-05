package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;


    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Usuario usuario = new Usuario(1L, "Rodrigo", "rodrigo@email.com");
        Mockito.when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("Rodrigo", resultado.getNome());
        assertEquals("rodrigo@email.com", resultado.getEmail());
    }


    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Mockito.when(usuarioRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarUsuarioPorId(999L)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
    }


    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario(10L, "Ana", "ana@email.com");

        Mockito.when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Usuario resultado = usuarioService.salvarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNome());
        assertEquals("ana@email.com", resultado.getEmail());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
    }
}
