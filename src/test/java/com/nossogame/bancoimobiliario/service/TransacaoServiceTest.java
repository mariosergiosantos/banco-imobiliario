package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.dto.ComprarPropriedadeBancoRequestDto;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Propriedade;
import com.nossogame.bancoimobiliario.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO teste só sera feito depois de implementar o strategy
/*@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private PropriedadeService propriedadeService;

    @Mock
    private JogadorService jogadorService;

    @Test
    void deveRegistrarPagamentoDeAluguelComSucesso() throws ResourceNotFoundException {
        Propriedade propriedade = new Propriedade("12345", "Leblon", null, 100, 6);
        Jogador origem = new Jogador("JogadorA", 500);
        Jogador destino = new Jogador("JogadorB", 1500);

        when(propriedadeService.buscarPropriedade("12345", "ABCDE1")).thenReturn(propriedade);
        when(jogadorService.buscarJodagor("JogadorA", "ABCDE1")).thenReturn(origem);
        when(jogadorService.buscarJodagor("JogadorB", "ABCDE1")).thenReturn(destino);

        TransacaoDto resultado = transacaoService.pagamentoAluguel("ABCDE1", "JogadorA", new PagamentoAluguelRequestDto("12345"));

        assertEquals("JogadorA", resultado.getOrigem());
        assertEquals("JogadorB", resultado.getDestino());
        assertEquals(6, resultado.getValor());
        verify(jogadorService).atualizarSaldo("JogadorA", 494); // 500 - 6
        verify(jogadorService).atualizarSaldo("JogadorB", 1506); // 1500 + 6
    }

    @Test
    void deveRegistrarCompraDePropriedadeDoBanco() throws ResourceNotFoundException {
        Propriedade propriedade = new Propriedade("12345", "Leblon", null, 300, 0);
        Jogador jogador = new Jogador("JogadorA", 500);

        when(propriedadeService.buscarPropriedade("12345", "ABCDE1")).thenReturn(propriedade);
        when(jogadorService.buscarJodagor("JogadorA", "ABCDE1")).thenReturn(jogador);

        TransacaoDto resultado = transacaoService.comprarPropriedadeBanco("ABCDE1", new ComprarPropriedadeBancoRequestDto("JogadorA", "12345"));

        assertEquals("JogadorA", resultado.getOrigem());
        assertNull(resultado.getDestino());
        assertEquals(300, resultado.getValor());
        verify(propriedadeService).atualizarPropriedade(propriedade);
        verify(jogadorService).atualizarSaldo("JogadorA", 200); // 500 - 300
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficienteParaCompra() throws ResourceNotFoundException {
        Propriedade propriedade = new Propriedade("12345", "Leblon", null, 500, 0);
        Jogador jogador = new Jogador("JogadorA", 300);

        when(propriedadeService.buscarPropriedade("12345", "ABCDE1")).thenReturn(propriedade);
        when(jogadorService.buscarJodagor("JogadorA", "ABCDE1")).thenReturn(jogador);

        Exception exception = assertThrows(RegraNegocialException.class, () -> {
            transacaoService.comprarPropriedadeBanco("ABCDE1", new ComprarPropriedadeBancoRequestDto("JogadorA", "12345"));
        });

        assertEquals("Saldo insuficiente para comprar a propriedade.", exception.getMessage());
    }
}
*/