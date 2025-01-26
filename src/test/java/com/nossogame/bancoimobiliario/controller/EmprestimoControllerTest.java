package com.nossogame.bancoimobiliario.controller;

import br.com.six2six.fixturefactory.Fixture;
import com.nossogame.bancoimobiliario.AbstractTest;
import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.dto.SolicitarEmprestimoDto;
import com.nossogame.bancoimobiliario.service.EmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class EmprestimoControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmprestimoService emprestimoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarEmprestimoComSucesso() throws Exception {

        EmprestimoDto emprestimo = Fixture.from(EmprestimoDto.class).gimme("valido");

        when(emprestimoService.solicitarEmprestimo(any(SolicitarEmprestimoDto.class))).thenReturn(emprestimo);

        mockMvc.perform(post("/api/v1/emprestimos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "pagadorId": "JogadorA",
                                        "recebedorId": "JogadorB",
                                        "valorContratado": 500,
                                        "valorAcordado": 600,
                                        "salaId": "D5D48"
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(emprestimo.getId()))
                .andExpect(jsonPath("$.pagadorId").value(emprestimo.getPagadorId()))
                .andExpect(jsonPath("$.recebedorId").value(emprestimo.getRecebedorId()))
                .andExpect(jsonPath("$.valorContratado").value(500))
                .andExpect(jsonPath("$.valorDevolucao").value(600))
                .andExpect(jsonPath("$.saldoDevedor").value(600))
                .andExpect(jsonPath("$.status").value("PENDENTE"))
                .andExpect(header().exists("X-Correlation-Id"));
    }

    /*@Test
    void devePagarEmprestimoComSucesso() throws Exception {
        EmprestimoDto emprestimoDto = new EmprestimoDto("1", "JogadorA", "JogadorB", 500, 600, "PAGO");

        when(emprestimoService.pagarEmprestimo(eq("ABCDE1"), eq("1"))).thenReturn(emprestimoDto);

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/emprestimos/1/pagar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ENCERRADO"));
    }*/
}