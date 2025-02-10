package com.nossogame.bancoimobiliario.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.VencedorJogoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.mapper.RankingMapper;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Ranking;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.validation.GameValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JogoServiceTest extends AbstractTest {

    @InjectMocks
    private JogoService jogoService;

    @Mock
    private SalaService salaService;

    @Mock
    private RankingService rankingService;

    @Mock
    private JogadorService jogadorService;

    @Mock
    private PropriedadeService propriedadeService;

    @Mock
    private GameValidation gameValidation;

    @Mock
    private EmprestimoService emprestimoService;

    @Test
    void deveIniciarJogoComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada-com-jogadores", new Rule() {{
            add("id", codigoSala);
        }});

        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);

        doCallRealMethod().when(gameValidation).startGameValidate(sala);
        doNothing().when(propriedadeService).cadastrarPropriedadesBase(sala);

        assertDoesNotThrow(() -> jogoService.iniciar(codigoSala));

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(gameValidation).startGameValidate(sala);
        verify(propriedadeService).cadastrarPropriedadesBase(sala);
    }

    @Test
    void deveLancarExcecaoAoIniciarJogoSemJogadores() throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
        }});

        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(gameValidation).startGameValidate(sala);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                jogoService.iniciar(codigoSala));

        assertEquals("Necessário ter ao menos 2 jogadores", exception.getMessage());

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(gameValidation).startGameValidate(sala);
        verify(propriedadeService, never()).cadastrarPropriedadesBase(sala);
    }

    @CsvSource({
            "EM_ANDAMENTO",
            "ENCERRADA"
    })
    @ParameterizedTest
    void deveLancarExcecaoAoIniciarJogoComStatusSalaIncorreto(String statusSala) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
            add("status", StatusSala.valueOf(statusSala));
        }});

        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(gameValidation).startGameValidate(sala);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                jogoService.iniciar(codigoSala));

        assertEquals("Status da sala não permite inicio do jogo", exception.getMessage());

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(gameValidation).startGameValidate(sala);
        verify(propriedadeService, never()).cadastrarPropriedadesBase(sala);
    }

    @Test
    void deveEncerrarJogoComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-em-andamento-com-jogadores", new Rule() {{
            add("id", codigoSala);
        }});

        Jogador jogador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Ranking ranking = Fixture.from(Ranking.class).gimme("valido");

        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(gameValidation).endGameValidation(sala);
        when(jogadorService.buscarJogadoresPorSala(codigoSala)).thenReturn(Collections.singletonList(jogador));
        when(emprestimoService.existsByRecebedorAndStatus(jogador, StatusEmprestimo.PENDENTE)).thenReturn(false);
        when(propriedadeService.calcularValorTotalPropriedades(jogador)).thenReturn(1000.0);
        when(rankingService.registrarVitoria(jogador, sala)).thenReturn(RankingMapper.INSTANCE.toDto(ranking));

        VencedorJogoDto resultado = jogoService.finalizar(codigoSala);

        assertEquals(codigoSala, resultado.salaId());
        assertEquals(jogador.getNome(), resultado.nome());
        assertEquals(2158000, resultado.saldo());
        assertEquals(1000, resultado.saldoPropriedades());

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(gameValidation).endGameValidation(sala);
        verify(jogadorService).buscarJogadoresPorSala(codigoSala);
        verify(emprestimoService).existsByRecebedorAndStatus(jogador, StatusEmprestimo.PENDENTE);
        verify(propriedadeService).calcularValorTotalPropriedades(jogador);
        verify(rankingService).registrarVitoria(jogador, sala);
    }

    @Test
    void deveLancarExcecaoPorNaoTerVencedor() throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-em-andamento-com-jogadores", new Rule() {{
            add("id", codigoSala);
        }});

        Jogador jogador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(gameValidation).endGameValidation(sala);
        when(jogadorService.buscarJogadoresPorSala(codigoSala)).thenReturn(Collections.singletonList(jogador));
        when(emprestimoService.existsByRecebedorAndStatus(jogador, StatusEmprestimo.PENDENTE)).thenReturn(true);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () -> {
            jogoService.finalizar(codigoSala);
        });

        assertEquals("Nenhum jogador é elegível para vencer a partida.", exception.getMessage());

        verify(salaService).buscarSalaPorId(codigoSala);
        verify(gameValidation).endGameValidation(sala);
        verify(jogadorService).buscarJogadoresPorSala(codigoSala);
        verify(emprestimoService).existsByRecebedorAndStatus(jogador, StatusEmprestimo.PENDENTE);
        verify(propriedadeService, never()).calcularValorTotalPropriedades(jogador);
        verify(rankingService, never()).registrarVitoria(jogador, sala);
    }

    @CsvSource({
            "ABERTA",
            "ENCERRADA"
    })
    @ParameterizedTest
    void deveLancarExcecaoAoEncerrarJogoNaoIniciado(String statusSala) throws ResourceNotFoundException, RegraNegocialException {
        Sala sala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
            add("status", StatusSala.valueOf(statusSala));
        }});

        when(salaService.buscarSalaPorId(codigoSala)).thenReturn(sala);
        doCallRealMethod().when(gameValidation).endGameValidation(sala);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () -> {
            jogoService.finalizar(codigoSala);
        });

        assertEquals("Status da sala não permite finalizar jogo", exception.getMessage());
    }
}