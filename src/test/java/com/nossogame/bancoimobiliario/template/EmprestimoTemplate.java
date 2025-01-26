package com.nossogame.bancoimobiliario.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.model.Emprestimo;
import com.nossogame.bancoimobiliario.model.Jogador;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmprestimoTemplate implements TemplateLoader {
    @Override
    public void load() {
         Fixture.of(Emprestimo.class).addTemplate("valido", new Rule() {{
             add("id", UUID.randomUUID().toString());
             add("recebedor", one(Jogador.class, "valido"));
             add("pagador", one(Jogador.class, "valido"));
             add("valorContratado", 500d);
             add("valorDevolucao", 600d);
             add("saldoDevedor", 600d);
             add("status", StatusEmprestimo.PENDENTE);
             add("dataEmprestimo", LocalDateTime.now());
         }});
    }
}
