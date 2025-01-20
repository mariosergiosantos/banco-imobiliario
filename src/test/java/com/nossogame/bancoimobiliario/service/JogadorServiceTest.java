package com.nossogame.bancoimobiliario.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.JogadorDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.repository.JogadorRepository;
import com.nossogame.bancoimobiliario.service.validation.PlayerValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALDO_INICIAL_JOGADOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JogadorServiceTest extends AbstractTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private SalaService salaService;

    @Mock
    private PlayerValidation playerValidation;

    @Test
    void deveAdicionarJogadorComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada-com-jogadores", new Rule() {{
            add("id", codigoSala);
        }});

        Jogador novoJogador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});


        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(playerValidation).validateCreatePlayer(sala);
        when(jogadorRepository.save(any(Jogador.class))).thenReturn(novoJogador);

        JogadorDto resultado = jogadorService.adicionarJogador(codigoSala, novoJogador);

        assertNotNull(resultado);
        assertEquals(novoJogador.getId(), resultado.getId());
        assertEquals(novoJogador.getNome(), resultado.getNome());
        assertEquals(novoJogador.getSaldo(), resultado.getSaldo());
        assertEquals(SALDO_INICIAL_JOGADOR, resultado.getSaldo());

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(playerValidation).validateCreatePlayer(sala);
        verify(jogadorRepository).save(novoJogador);
    }

    @Test
    void deveLancarExcecaoQuandoNomeDuplicadoNaSala() throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada-com-jogadores", new Rule() {{
            add("id", codigoSala);
        }});

        Jogador novoJogador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});


        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(playerValidation).validateCreatePlayer(sala);
        when(jogadorRepository.save(any(Jogador.class))).thenThrow(new DataIntegrityViolationException(""));

        Exception exception = assertThrows(RegraNegocialException.class,
                () -> jogadorService.adicionarJogador(codigoSala, novoJogador));

        assertNotNull(exception);
        assertEquals("Nome já cadastrado para outro jogador", exception.getMessage());

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(playerValidation).validateCreatePlayer(sala);
        verify(jogadorRepository).save(novoJogador);
    }

    @Test
    void deveCreditarSaldoComSucesso() throws ResourceNotFoundException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        when(jogadorRepository.findById(jogador.getId())).thenReturn(Optional.of(jogador));

        jogadorService.creditarSaldo(jogador.getId(), 2000);

        verify(jogadorRepository).save(jogador);
        assertEquals(SALDO_INICIAL_JOGADOR + 2000, jogador.getSaldo());
    }

    @Test
    void deveDebitarSaldoComSucesso() throws ResourceNotFoundException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        when(jogadorRepository.findById(jogador.getId())).thenReturn(Optional.of(jogador));

        jogadorService.debitarSaldo(jogador.getId(), 2000);

        verify(jogadorRepository).save(jogador);
        assertEquals(SALDO_INICIAL_JOGADOR - 2000, jogador.getSaldo());
    }
}
