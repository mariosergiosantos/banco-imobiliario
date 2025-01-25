package com.nossogame.bancoimobiliario.template.dto;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.nossogame.bancoimobiliario.dto.EmprestimoDto;
import com.nossogame.bancoimobiliario.model.enuns.StatusEmprestimo;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmprestimoTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(EmprestimoDto.class).addTemplate("valido", new Rule() {{
            add("id", UUID.randomUUID().toString());
            add("jogadorOrigemId", UUID.randomUUID().toString());
            add("jogadorDestinoId", UUID.randomUUID().toString());
            add("valorContratado", 500d);
            add("valorDevolucao", 600d);
            add("saldoDevedor", 600d);
            add("status", StatusEmprestimo.PENDENTE);
            add("dataEmprestimo", LocalDateTime.now());
        }});
    }
}
