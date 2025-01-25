package com.nossogame.bancoimobiliario.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.model.Jogador;

import java.util.UUID;

import static com.nossogame.bancoimobiliario.config.AppConstantes.SALDO_INICIAL_JOGADOR;
import static com.nossogame.bancoimobiliario.model.enuns.StatusJogador.ATIVO;

public class JogadorTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Jogador.class).addTemplate("valido", new Rule() {{
            add("id", UUID.randomUUID().toString());
            add("nome", "Jogador 1");
            add("saldo", SALDO_INICIAL_JOGADOR);
            //add("status", ATIVO);
        }});
    }
}
