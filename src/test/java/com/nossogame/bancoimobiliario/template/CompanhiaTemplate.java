package com.nossogame.bancoimobiliario.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.model.Companhia;

import java.util.UUID;

public class CompanhiaTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Companhia.class).addTemplate("companhia", new Rule() {{
            add("id", UUID.randomUUID().toString());
            add("nome", "Jogador 1");
            add("valorCompra", 60000d);
            add("aluguelBase", 1000d);
            add("hipotecada", false);
            add("valorAluguelAtual", 1500d);
        }});

        Fixture.of(Companhia.class).addTemplate("companhia-hipotecada", new Rule() {{
            add("id", UUID.randomUUID().toString());
            add("nome", "Jogador 1");
            add("valorCompra", 60000d);
            add("aluguelBase", 1000d);
            add("hipotecada", false);
            add("valorAluguelAtual", 1500d);
        }});
    }
}
