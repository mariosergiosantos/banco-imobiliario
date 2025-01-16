package com.nossogame.bancoimobiliario.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.SalaDto;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.repository.SalaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest extends AbstractTest {

    @InjectMocks
    private SalaService salaService;

    @Mock
    private SalaRepository salaRepository;

    @Test
    void deveCriarSalaComSucesso() {
        Sala novaSala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
        }});

        when(salaRepository.save(any(Sala.class))).thenReturn(novaSala);

        SalaDto result = salaService.criarSala();

        assertNotNull(result);
        assertEquals(codigoSala, result.getId());
        assertEquals(StatusSala.ABERTA, result.getStatus());
        assertNull(result.getJogadores());
        assertNull(result.getPropriedades());

        verify(salaRepository, times(1)).save(any(Sala.class));
    }

    /*@Test
    void deveLancarExcecaoQuandoIdDuplicado() {
        String codigoSala = CodigoAleatorio.gerarCodigo();

        Sala novaSala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
        }});

        when(salaRepository.existsById("ABCDE1")).thenReturn(true);

        // Execução do método e verificação
        Exception exception = assertThrows(RegraNegocialException.class,
                () -> salaService.criarSala());

        assertEquals("Sala com este ID já existe.", exception.getMessage());
        verify(salaRepository, never()).save(any(Sala.class));
    }*/
}

