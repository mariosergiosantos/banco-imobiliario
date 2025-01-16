package com.nossogame.bancoimobiliario;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.nossogame.bancoimobiliario.service.CodigoAleatorio;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest {

    protected static String codigoSala;

    @BeforeAll
    static void setUp() {
        codigoSala = CodigoAleatorio.gerarCodigo();
        FixtureFactoryLoader.loadTemplates("com.nossogame.bancoimobiliario.template");
    }
}
