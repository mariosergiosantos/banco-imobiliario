package com.nossogame.bancoimobiliario.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.ConstruirPropriedadeRequestDto;
import com.nossogame.bancoimobiliario.dto.TransacaoDto;
import com.nossogame.bancoimobiliario.exception.RegraNegocialException;
import com.nossogame.bancoimobiliario.exception.ResourceNotFoundException;
import com.nossogame.bancoimobiliario.model.Casa;
import com.nossogame.bancoimobiliario.model.Companhia;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;
import com.nossogame.bancoimobiliario.repository.PropriedadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropriedadeServiceTest extends AbstractTest {

    @InjectMocks
    private PropriedadeService propriedadeService;

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private JogadorService jogadorService;

    /*@Test
    void deveListarPropriedadesDeUmaSala() {
        List<Propriedade> propriedades = new ArrayList<>();
        propriedades.addAll(Fixture.from(Propriedade.class).gimme(3, "casa"));
        propriedades.addAll(Fixture.from(Propriedade.class).gimme(3, "companhia"));
        propriedades.addAll(Fixture.from(Propriedade.class).gimme(3, "hotel"));

        when(propriedadeRepository.findBySalaId("ABCDE1")).thenReturn(propriedades);

        List<Propriedade> resultado = propriedadeService.listarPropriedadesDaSala("ABCDE1");

        assertEquals(2, resultado.size());
        assertEquals("Leblon", resultado.get(0).getNome());
        assertEquals("Avenida Paulista", resultado.get(1).getNome());
        verify(propriedadeRepository).findBySalaId("ABCDE1");
    }*/

    @Test
    void deveConstruirPropriedadeComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        Rule rule = new Rule() {{
            add("dono", jogador);
        }};

        Casa casa = Fixture.from(Casa.class).gimme("casa", rule);

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(casa));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);
        when(propriedadeRepository.findBySalaIdAndCor(anyString(), any(CorPropriedade.class)))
                .thenReturn(Fixture.from(Casa.class).gimme(3, "casa", rule));
        when(jogadorService.debitarSaldo(any(Jogador.class), anyDouble())).thenReturn(jogador);
        when(propriedadeRepository.save(casa)).thenReturn(casa);

        TransacaoDto resultado = propriedadeService.construirPropriedade(UUID.randomUUID().toString(), casa.getId(), requestDto);

        assertNotNull(resultado);

        assertEquals(2, casa.getNumeroCasas());

        verify(propriedadeRepository).findById(casa.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository).save(casa);
    }

    @Test
    void deveConstruirHotelComSucesso() throws ResourceNotFoundException, RegraNegocialException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        Rule rule = new Rule() {{
            add("dono", jogador);
            add("numeroCasas", 3);
        }};

        Casa casa = Fixture.from(Casa.class).gimme("casa", rule);

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(casa));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);
        when(propriedadeRepository.findBySalaIdAndCor(anyString(), any(CorPropriedade.class)))
                .thenReturn(Fixture.from(Casa.class).gimme(3, "casa", rule));
        when(jogadorService.debitarSaldo(any(Jogador.class), anyDouble())).thenReturn(jogador);
        when(propriedadeRepository.save(casa)).thenReturn(casa);

        TransacaoDto resultado = propriedadeService.construirPropriedade(UUID.randomUUID().toString(), casa.getId(), requestDto);

        assertNotNull(resultado);

        assertEquals(0, casa.getNumeroCasas());

        verify(propriedadeRepository).findById(casa.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository).save(casa);
    }

    @Test
    void deveLancarExcecaoQuandoTentaContruirQuandoNaoéCasa() throws ResourceNotFoundException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        Companhia companhia = Fixture.from(Companhia.class).gimme("companhia", new Rule() {{
            add("dono", jogador);
        }});

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(companhia));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                propriedadeService.construirPropriedade(UUID.randomUUID().toString(), companhia.getId(), requestDto));

        assertEquals("Apenas propriedades do tipo Casa permitem construção.", exception.getMessage());

        verify(propriedadeRepository).findById(companhia.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository, never()).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService, never()).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository, never()).save(companhia);
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficienteParaConstruir() throws ResourceNotFoundException, RegraNegocialException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("saldo", 50d);
        }});

        Casa casa = Fixture.from(Casa.class).gimme("casa", new Rule() {{
            add("dono", jogador);
        }});

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(casa));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                propriedadeService.construirPropriedade(UUID.randomUUID().toString(), casa.getId(), requestDto));

        assertEquals("Saldo insuficiente para construir.", exception.getMessage());

        verify(propriedadeRepository).findById(casa.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository, never()).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService, never()).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository, never()).save(casa);
    }

    @Test
    void deveLancarExcecaoQuandoNaoPossuiTodasAsPropriedadesDaMesmaCor() throws ResourceNotFoundException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        Casa casa = Fixture.from(Casa.class).gimme("casa", new Rule() {{
            add("dono", jogador);
        }});

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(casa));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);
        when(propriedadeRepository.findBySalaIdAndCor(anyString(), any(CorPropriedade.class)))
                .thenReturn(Fixture.from(Casa.class).gimme(3, "casa"));

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                propriedadeService.construirPropriedade(UUID.randomUUID().toString(), casa.getId(), requestDto));

        assertEquals("Apenas propriedades do tipo Casa permitem construção.", exception.getMessage());

        verify(propriedadeRepository).findById(casa.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService, never()).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository, never()).save(casa);
    }

    @Test
    void deveLancarExcecaoQuandoPropriedadeEstaHipotecada() throws ResourceNotFoundException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        Casa casa = Fixture.from(Casa.class).gimme("casa-hipotecadada", new Rule() {{
            add("dono", jogador);
        }});

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(casa));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                propriedadeService.construirPropriedade(UUID.randomUUID().toString(), casa.getId(), requestDto));

        assertEquals("Não é possível construir em propriedades hipotecadas.", exception.getMessage());

        verify(propriedadeRepository).findById(casa.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository, never()).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService, never()).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository, never()).save(casa);
    }

    @Test
    void deveLancarExcecaoQuandoPropriedadeEHotel() throws ResourceNotFoundException {
        Jogador jogador = Fixture.from(Jogador.class).gimme("valido");

        Casa casa = Fixture.from(Casa.class).gimme("hotel", new Rule() {{
            add("dono", jogador);
        }});

        ConstruirPropriedadeRequestDto requestDto = new ConstruirPropriedadeRequestDto();
        requestDto.setJogadorId(jogador.getId());

        when(propriedadeRepository.findById(anyString())).thenReturn(Optional.of(casa));
        when(jogadorService.findById(jogador.getId())).thenReturn(jogador);

        RegraNegocialException exception = assertThrows(RegraNegocialException.class, () ->
                propriedadeService.construirPropriedade(UUID.randomUUID().toString(), casa.getId(), requestDto));

        assertEquals("A propriedade já atingiu o limite de construções.", exception.getMessage());

        verify(propriedadeRepository).findById(casa.getId());
        verify(jogadorService).findById(jogador.getId());
        verify(propriedadeRepository, never()).findBySalaIdAndCor(anyString(), any(CorPropriedade.class));
        verify(jogadorService, never()).debitarSaldo(any(Jogador.class), anyDouble());
        verify(propriedadeRepository, never()).save(casa);
    }

    /*@Test
    void deveHipotecarPropriedadeComSucesso() {
        Casa casa = new Casa("1", "Leblon", CorPropriedade.ROXO, 100, 6, null);

        when(propriedadeRepository.findById("1")).thenReturn(Optional.of(casa));

        propriedadeService.gerenciarHipoteca("ABCDE1", "1", true);

        assertTrue(casa.isHipotecada());
        verify(propriedadeRepository).save(casa);
    }*/
}
