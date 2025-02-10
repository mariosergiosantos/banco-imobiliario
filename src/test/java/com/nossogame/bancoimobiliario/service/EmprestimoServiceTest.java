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
import com.nossogame.bancoimobiliario.model.Transacao;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.repository.EmprestimoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

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

    @Mock
    private TransacaoService transacaoService;

    @Mock
    private SalaService salaService;

    @Test
    void deveCriarEmprestimoComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador recebedor = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
            add("id", UUID.randomUUID().toString());
        }});

        Jogador pagador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
            add("id", UUID.randomUUID().toString());
        }});

        Emprestimo novoEmprestimo = Fixture.from(Emprestimo.class).gimme("valido", new Rule() {{
            add("recebedor", recebedor);
            add("pagador", pagador);
        }});

        when(salaService.buscarSalaPorId(sala.getId())).thenReturn(sala);
        when(jogadorService.findById(pagador.getId())).thenReturn(pagador);
        when(jogadorService.findById(recebedor.getId())).thenReturn(recebedor);
        when(jogadorService.debitarSaldo(pagador, 500)).thenReturn(pagador);
        when(jogadorService.creditarSaldo(recebedor, 600)).thenReturn(recebedor);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(novoEmprestimo);

        SolicitarEmprestimoDto solicitarEmprestimoDto = new SolicitarEmprestimoDto(sala.getId(), recebedor.getId(), pagador.getId(), 500, 600);

        EmprestimoDto resultado = emprestimoService.solicitarEmprestimo(solicitarEmprestimoDto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getDataEmprestimo());

        assertEquals(recebedor.getId(), resultado.getRecebedorId());
        assertEquals(pagador.getId(), resultado.getPagadorId());
        assertEquals(500, resultado.getValorContratado());
        assertEquals(600, resultado.getValorDevolucao());

        verify(jogadorService, times(2)).findById(anyString());
        verify(jogadorService).debitarSaldo(pagador, 500);
        verify(jogadorService).creditarSaldo(recebedor, 600);
        verify(emprestimoRepository).save(any(Emprestimo.class));
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficienteParaEmprestimo() throws ResourceNotFoundException {
        Jogador origem = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
            add("saldo", 400d);
        }});

        Jogador destino = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        sala.setStatus(StatusSala.EM_ANDAMENTO);

        when(jogadorService.findById(origem.getId())).thenReturn(origem);
        when(salaService.buscarSalaPorId(sala.getId())).thenReturn(sala);

        SolicitarEmprestimoDto solicitarEmprestimoDto = new SolicitarEmprestimoDto(sala.getId(), origem.getId(), destino.getId(), 500, 600);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () -> emprestimoService.solicitarEmprestimo(solicitarEmprestimoDto));

        assertEquals("O jogador não possui saldo suficiente para conceder o empréstimo.", exception.getMessage());

        verify(jogadorService).findById(anyString());
        verify(jogadorService, never()).debitarSaldo(origem, 500);
        verify(jogadorService, never()).creditarSaldo(destino, 600);
        verify(emprestimoRepository, never()).save(any(Emprestimo.class));
    }

    @Test
    void devePagarEmprestimoTotalComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador recebedor = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Jogador pagador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Emprestimo emprestimo = Fixture.from(Emprestimo.class).gimme("valido", new Rule() {{
            add("recebedor", recebedor);
            add("pagador", pagador);
        }});

        when(emprestimoRepository.findById(emprestimo.getId())).thenReturn(Optional.of(emprestimo));
        when(jogadorService.debitarSaldo(pagador, 600)).thenReturn(pagador);
        when(jogadorService.creditarSaldo(recebedor, 600)).thenReturn(recebedor);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);
        when(transacaoService.save(any())).thenReturn(new Transacao());

        EmprestimoDto result = emprestimoService.
                pagarEmprestimo(emprestimo.getId(), 600);

        assertEquals(StatusEmprestimo.ENCERRADO, result.getStatus());
        assertEquals(0, result.getSaldoDevedor());

        verify(emprestimoRepository).findById(emprestimo.getId());
        verify(jogadorService).debitarSaldo(pagador, 600);
        verify(jogadorService).creditarSaldo(recebedor, 600);
        verify(emprestimoRepository).save(any(Emprestimo.class));
        verify(transacaoService).save(any(Transacao.class));
    }

    @Test
    void devePagarEmprestimoParcialComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador recebedor = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Jogador pagador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});

        Emprestimo emprestimo = Fixture.from(Emprestimo.class).gimme("valido", new Rule() {{
            add("recebedor", recebedor);
            add("pagador", pagador);
        }});

        when(emprestimoRepository.findById(emprestimo.getId())).thenReturn(Optional.of(emprestimo));
        when(jogadorService.debitarSaldo(pagador, 200)).thenReturn(pagador);
        when(jogadorService.creditarSaldo(recebedor, 200)).thenReturn(recebedor);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        EmprestimoDto result = emprestimoService.pagarEmprestimo(emprestimo.getId(), 200);

        assertEquals(StatusEmprestimo.PENDENTE, result.getStatus());
        assertEquals(400, result.getSaldoDevedor());

        verify(emprestimoRepository).findById(emprestimo.getId());
        verify(jogadorService).debitarSaldo(pagador, 200);
        verify(jogadorService).creditarSaldo(recebedor, 200);
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