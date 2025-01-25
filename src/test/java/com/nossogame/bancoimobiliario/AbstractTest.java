package com.nossogame.bancoimobiliario;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.service.CodigoAleatorio;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest {

    protected static String codigoSala;

    protected static Sala sala;

    protected static Jogador jogador;

    @BeforeAll
    static void setUp() {
        codigoSala = CodigoAleatorio.gerarCodigo();
        FixtureFactoryLoader.loadTemplates("com.nossogame.bancoimobiliario.template");

        sala = Fixture.from(Sala.class).gimme("valida-criada", new Rule() {{
            add("id", codigoSala);
        }});

        jogador = Fixture.from(Jogador.class).gimme("valido", new Rule() {{
            add("sala", sala);
        }});
    }
}
