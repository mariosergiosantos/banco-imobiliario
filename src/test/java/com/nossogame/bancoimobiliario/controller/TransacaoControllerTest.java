package com.nossogame.bancoimobiliario.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/*
@SpringBootTest
@AutoConfigureMockMvc
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoService transacaoService;

    @Test
    void deveRegistrarTransacaoDePagamentoDeAluguelComSucesso() throws Exception {
        TransacaoDto transacaoDto = new TransacaoDto("1", "JogadorA", "JogadorB", 200, "PAGAMENTO_ALUGUEL");

        when(transacaoService.pagamentoAluguel(eq("ABCDE1"), eq("JogadorA"), any(PagamentoAluguelRequestDto.class)))
                .thenReturn(transacaoDto);

        mockMvc.perform(post("/api/v1/transacoes/salas/ABCDE1/jogadores/JogadorA/pagamento-aluguel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "propriedadeId": "12345"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.origem").value("JogadorA"))
                .andExpect(jsonPath("$.destino").value("JogadorB"))
                .andExpect(jsonPath("$.valor").value(200))
                .andExpect(jsonPath("$.tipo").value("PAGAMENTO_ALUGUEL"));
    }

    @Test
    void deveRegistrarTransacaoDeCompraDePropriedadeComSucesso() throws Exception {
        TransacaoDto transacaoDto = new TransacaoDto("2", "JogadorA", null, 300, "COMPRA_DO_BANCO");

        when(transacaoService.comprarPropriedadeBanco(eq("ABCDE1"), any(ComprarPropriedadeBancoRequestDto.class)))
                .thenReturn(transacaoDto);

        mockMvc.perform(post("/api/v1/transacoes/salas/ABCDE1/comprar-propriedade-banco")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "jogadorId": "JogadorA",
                        "propriedadeId": "12345"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.origem").value("JogadorA"))
                .andExpect(jsonPath("$.valor").value(300))
                .andExpect(jsonPath("$.tipo").value("COMPRA_DO_BANCO"));
    }

    @Test
    void deveListarTransacoesDeUmaSala() throws Exception {
        List<TransacaoDto> transacoes = List.of(
                new TransacaoDto("1", "JogadorA", "JogadorB", 200, "PAGAMENTO_ALUGUEL"),
                new TransacaoDto("2", "JogadorA", null, 300, "COMPRA_DO_BANCO")
        );

        when(transacaoService.listarTransacoesDaSala("ABCDE1")).thenReturn(transacoes);

        mockMvc.perform(get("/api/v1/transacoes/salas/ABCDE1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].tipo").value("PAGAMENTO_ALUGUEL"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].tipo").value("COMPRA_DO_BANCO"));
    }
}

*/