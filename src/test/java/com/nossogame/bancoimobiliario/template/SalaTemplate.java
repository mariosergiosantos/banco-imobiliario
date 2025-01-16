package com.nossogame.bancoimobiliario.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.Sala;
import com.nossogame.bancoimobiliario.model.enuns.StatusSala;
import com.nossogame.bancoimobiliario.service.CodigoAleatorio;

import java.time.LocalDateTime;

public class SalaTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Sala.class).addTemplate("valida-criada", new Rule() {{
            add("id", CodigoAleatorio.gerarCodigo());
            add("dataCriacao", LocalDateTime.now());
            add("status", StatusSala.ABERTA);
        }});

        Fixture.of(Sala.class).addTemplate("valida-criada-com-jogadores", new Rule() {{
            add("id", CodigoAleatorio.gerarCodigo());
            add("dataCriacao", LocalDateTime.now());
            add("status", StatusSala.ABERTA);
            add("jogadores", has(2).of(Jogador.class, "valido"));
        }});

        Fixture.of(Sala.class).addTemplate("valida-em-andamento-com-jogadores", new Rule() {{
            add("id", CodigoAleatorio.gerarCodigo());
            add("dataCriacao", LocalDateTime.now());
            add("status", StatusSala.EM_ANDAMENTO);
            add("jogadores", has(2).of(Jogador.class, "valido"));
        }});

        /*Fixture.of(Sala.class).addTemplate("valida", new Rule() {{
            add("id", 1);
            add("nome", "Sala 1");
            add("jogadores", has(2).of(Jogador.class, "valido"));
        }});

        Fixture.of(Sala.class).addTemplate("valida", new Rule() {{
            add("id", 1);
            add("nome", "Sala 1");
            add("jogadores", has(2).of(Jogador.class, "valido"));
        }});*/
    }
}
