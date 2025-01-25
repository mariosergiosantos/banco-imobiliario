package com.nossogame.bancoimobiliario.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Ranking;

import java.time.LocalDateTime;

public class RankingTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Ranking.class).addTemplate("valido", new Rule() {{
            add("id", 1L);
            add("jogador", one(Jogador.class, "valido"));
            add("saldoFinal", 1000d);
            add("numeroPropriedades", 6);
            add("dataVitoria", LocalDateTime.now());
        }});
    }
}
