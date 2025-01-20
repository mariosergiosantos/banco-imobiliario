package com.nossogame.bancoimobiliario.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.model.Casa;
import com.nossogame.bancoimobiliario.model.enuns.CorPropriedade;

import java.util.UUID;

public class CasaTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Casa.class).addTemplate("casa", new Rule() {{
            add("cor", CorPropriedade.AMARELO);
            add("numeroCasas", 1);
            add("hotel", false);

            add("id", UUID.randomUUID().toString());
            add("nome", "Jogador 1");
            add("valorCompra", 60000d);
            add("aluguelBase", 1000d);
            add("hipotecada", false);
            add("valorAluguelAtual", 1500d);
        }});

        Fixture.of(Casa.class).addTemplate("casa-hipotecadada", new Rule() {{
            add("cor", CorPropriedade.AMARELO);
            add("numeroCasas", 1);
            add("hotel", false);

            add("id", UUID.randomUUID().toString());
            add("nome", "Jogador 1");
            add("valorCompra", 60000d);
            add("aluguelBase", 1000d);
            add("hipotecada", true);
            add("valorAluguelAtual", 1500d);
        }});

        Fixture.of(Casa.class).addTemplate("hotel", new Rule() {{
            add("cor", CorPropriedade.AMARELO);
            add("numeroCasas", 0);
            add("hotel", true);

            add("id", UUID.randomUUID().toString());
            add("nome", "Jogador 1");
            add("valorCompra", 60000d);
            add("aluguelBase", 1000d);
            add("hipotecada", false);
            add("valorAluguelAtual", 1500d);
        }});
    }
}
