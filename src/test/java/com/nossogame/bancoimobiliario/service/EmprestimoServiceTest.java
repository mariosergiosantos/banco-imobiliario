package com.nossogame.bancoimobiliario.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.dto.SolicitarEmprestimoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import com.nossogame.bancoimobiliario.repository.EmprestimoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest extends AbstractTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private JogadorService jogadorService;

    @Test
    void deveCriarEmprestimoComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador origem = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Jogador destino = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Emprestimo novoEmprestimo = Fixture.from(Emprestimo.class).gimme("valido");

        when(jogadorService.findById(origem.getId())).thenReturn(origem);
        when(jogadorService.findById(destino.getId())).thenReturn(destino);
        when(jogadorService.debitarSaldo(origem.getId(), 500)).thenReturn(origem);
        when(jogadorService.creditarSaldo(destino.getId(), 600)).thenReturn(destino);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(novoEmprestimo);

        SolicitarEmprestimoDto solicitarEmprestimoDto = new SolicitarEmprestimoDto();
        solicitarEmprestimoDto.setJogadorOrigemId(origem.getId());
        solicitarEmprestimoDto.setJogadorDestinoId(destino.getId());
        solicitarEmprestimoDto.setValorContratado(500);
        solicitarEmprestimoDto.setValorAcordado(600);


        EmprestimoDto resultado = emprestimoService.solicitarEmprestimo(solicitarEmprestimoDto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getDataEmprestimo());

        assertEquals(origem.getId(), resultado.getJogadorOrigemId());
        assertEquals(destino.getId(), resultado.getJogadorDestinoId());
        assertEquals(500, resultado.getValorContratado());
        assertEquals(600, resultado.getValorDevolucao());

        verify(jogadorService, times(2)).findById(anyString());
        verify(jogadorService).debitarSaldo(origem.getId(), 500);
        verify(jogadorService).creditarSaldo(destino.getId(), 600);
        verify(emprestimoRepository).save(any(Emprestimo.class));
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficienteParaEmprestimo() throws ResourceNotFoundException, RegraNegocialException {
        Jogador origem = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
            add("saldo", 400d);
        }});

        Jogador destino = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        when(jogadorService.findById(origem.getId())).thenReturn(origem);

        SolicitarEmprestimoDto solicitarEmprestimoDto = new SolicitarEmprestimoDto();
        solicitarEmprestimoDto.setJogadorOrigemId(origem.getId());
        solicitarEmprestimoDto.setJogadorDestinoId(destino.getId());
        solicitarEmprestimoDto.setValorContratado(500);
        solicitarEmprestimoDto.setValorAcordado(600);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () -> emprestimoService.solicitarEmprestimo(solicitarEmprestimoDto));

        assertEquals("O jogador não possui saldo suficiente para conceder o empréstimo.", exception.getMessage());

        verify(jogadorService).findById(anyString());
        verify(jogadorService, never()).debitarSaldo(origem.getId(), 500);
        verify(jogadorService, never()).creditarSaldo(destino.getId(), 600);
        verify(emprestimoRepository, never()).save(any(Emprestimo.class));
    }

    @Test
    void devePagarEmprestimoTotalComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador origem = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Jogador destino = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Emprestimo emprestimo = Fixture.from(Emprestimo.class).gimme("valido", new Rule() {{
            add("jogadorOrigem", origem);
            add("jogadorDestino", destino);
        }});

        when(emprestimoRepository.findById(emprestimo.getId())).thenReturn(Optional.of(emprestimo));
        when(jogadorService.debitarSaldo(origem.getId(), 600)).thenReturn(origem);
        when(jogadorService.creditarSaldo(destino.getId(), 600)).thenReturn(destino);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        EmprestimoDto result = emprestimoService.pagarEmprestimo(emprestimo.getId(), destino.getId(), 600);

        assertEquals(StatusEmprestimo.ENCERRADO, result.getStatus());
        assertEquals(0, result.getSaldoDevedor());

        verify(emprestimoRepository).findById(emprestimo.getId());
        verify(jogadorService).debitarSaldo(origem.getId(), 600);
        verify(jogadorService).creditarSaldo(destino.getId(), 600);
        verify(emprestimoRepository).save(any(Emprestimo.class));
    }

    @Test
    void devePagarEmprestimoParcialComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador origem = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Jogador destino = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Emprestimo emprestimo = Fixture.from(Emprestimo.class).gimme("valido", new Rule() {{
            add("jogadorOrigem", origem);
            add("jogadorDestino", destino);
        }});

        when(emprestimoRepository.findById(emprestimo.getId())).thenReturn(Optional.of(emprestimo));
        when(jogadorService.debitarSaldo(origem.getId(), 200)).thenReturn(origem);
        when(jogadorService.creditarSaldo(destino.getId(), 200)).thenReturn(destino);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        EmprestimoDto result = emprestimoService.pagarEmprestimo(emprestimo.getId(), destino.getId(), 200);

        assertEquals(StatusEmprestimo.PENDENTE, result.getStatus());
        assertEquals(400, result.getSaldoDevedor());

        verify(emprestimoRepository).findById(emprestimo.getId());
        verify(jogadorService).debitarSaldo(origem.getId(), 200);
        verify(jogadorService).creditarSaldo(destino.getId(), 200);
        verify(emprestimoRepository).save(any(Emprestimo.class));
    }

    /*@Test
    void deveLancarExcecaoQuandoPagarEmprestimoJaPago() {
        Emprestimo emprestimo = new Emprestimo("1", null, null, 500, 600, "PAGO");

        when(emprestimoRepository.findById("1")).thenReturn(Optional.of(emprestimo));

        Exception exception = assertThrows(RegraNegocialException.class, () -> {
            emprestimoService.pagarEmprestimo("ABCDE1", "1");
        });

        assertEquals("O empréstimo já foi pago.", exception.getMessage());
    }*/
}