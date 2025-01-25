package com.nossogame.bancoimobiliario.controller;

/*@SpringBootTest
@AutoConfigureMockMvc
class PropriedadeControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropriedadeService propriedadeService;

    @Test
    void deveListarPropriedadesDeUmaSala() throws Exception {
        // Simulação de retorno do serviço
        List<PropriedadeDto> propriedades = List.of(
                new PropriedadeDto("1", "Leblon", false, 100, 6, 0, false, null),
                new PropriedadeDto("2", "Avenida Paulista", false, 150, 20, 0, false, null)
        );

        when(propriedadeService.listarPropriedadesDaSala("ABCDE1")).thenReturn(propriedades);

        mockMvc.perform(get("/api/v1/salas/ABCDE1/propriedades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nome").value("Leblon"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].nome").value("Avenida Paulista"));
    }

    @Test
    void deveTransferirPropriedadeParaOutroJogador() throws Exception {
        PropriedadeDto propriedade = new PropriedadeDto("1", "Leblon", false, 100, 6, 0, false, "Jogador B");

        when(propriedadeService.transferirPropriedade(eq("ABCDE1"), eq("1"), eq("JogadorA"), eq("JogadorB")))
                .thenReturn(propriedade);

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/propriedades/1/transferir")
                        .param("origem", "JogadorA")
                        .param("destino", "JogadorB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Leblon"))
                .andExpect(jsonPath("$.dono").value("Jogador B"));
    }

    @Test
    void deveHipotecarPropriedade() throws Exception {
        PropriedadeDto propriedade = new PropriedadeDto("1", "Leblon", true, 100, 6, 0, true, "Jogador A");

        when(propriedadeService.gerenciarHipoteca(eq("ABCDE1"), eq("1"), eq(true))).thenReturn(propriedade);

        mockMvc.perform(patch("/api/v1/salas/ABCDE1/propriedades/1/hipoteca")
                        .param("acao", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.hipotecada").value(true));
    }
}
*/