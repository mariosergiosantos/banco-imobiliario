-- Criação das tabelas
CREATE TABLE sala (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    data_criacao TIMESTAMP NOT NULL,
    status ENUM('ABERTA', 'EM_ANDAMENTO', 'ENCERRADA') NOT NULL DEFAULT 'ABERTA',
    administrador_id CHAR(36)
);

CREATE TABLE jogador (
    id CHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    saldo DOUBLE NOT NULL,
    sala_id VARCHAR(7),
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE propriedade (
    id CHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cor ENUM('ROXO', 'CIANO', 'ROSA', 'LARANJA', 'VERMELHO', 'AMARELO', 'VERDE', 'AZUL'),
    valor_compra DOUBLE NOT NULL,
    aluguel_base DOUBLE NOT NULL,
    numero_casas INT,
    hotel BOOLEAN NOT NULL DEFAULT FALSE,
    valor_aluguel_atual DOUBLE NOT NULL,
    hipotecada BOOLEAN NOT NULL DEFAULT FALSE,
    companhia BOOLEAN NOT NULL DEFAULT FALSE,
    dono_id CHAR(36),
    sala_id VARCHAR(7) NOT NULL,
    tipo_propriedade VARCHAR(31) NOT NULL
);

CREATE TABLE transacao (
    id CHAR(36) NOT NULL PRIMARY KEY,
    sala_id VARCHAR(7) NOT NULL,
    origem_id CHAR(36),
    destino_id CHAR(36),
    propriedade_id CHAR(36),
    valor DOUBLE NOT NULL,
    tipo ENUM('COMPRA_PROPRIEDADE_DO_BANCO', 'COMPRA_PROPRIEDADE_JOGADOR', 'CONSTRUIR_PROPRIEDADE', 'PAGAMENTO_ALUGUEL', 'HIPOTECA', 'PAGAMENTO_SALARIO', 'EMPRESTIMO', 'PAGAMENTO_EMPRESTIMO') NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    descricao VARCHAR(100)
);

CREATE TABLE ranking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    jogador_id VARCHAR(50) NOT NULL,
    saldo_final DOUBLE NOT NULL,
    numero_propriedades INT NOT NULL,
    data_vitoria TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sala_id VARCHAR(7) NOT NULL
);

CREATE TABLE emprestimo (
    id CHAR(36) NOT NULL PRIMARY KEY,
    jogador_origem_id CHAR(36) NOT NULL,
    jogador_destino_id CHAR(36) NOT NULL,
    valor_contratado DOUBLE NOT NULL,
    valor_devolucao DOUBLE NOT NULL,
    saldo_devedor DOUBLE NOT NULL,
    data_emprestimo TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDENTE', 'ENCERRADO') NOT NULL DEFAULT 'PENDENTE'
);


-- Adição das constraints após a criação das tabelas
ALTER TABLE sala
    ADD CONSTRAINT fk_administrador FOREIGN KEY (administrador_id) REFERENCES jogador(id);

ALTER TABLE jogador
    ADD CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES sala(id);

ALTER TABLE jogador
    ADD CONSTRAINT uq_nome_sala UNIQUE (nome, sala_id);

ALTER TABLE propriedade
    ADD CONSTRAINT fk_dono FOREIGN KEY (dono_id) REFERENCES jogador(id);

ALTER TABLE propriedade
    ADD CONSTRAINT fk_sala_propriedade FOREIGN KEY (sala_id) REFERENCES sala(id);

ALTER TABLE transacao
    ADD CONSTRAINT fk_sala_transacao FOREIGN KEY (sala_id) REFERENCES sala(id);

ALTER TABLE transacao
    ADD CONSTRAINT fk_origem FOREIGN KEY (origem_id) REFERENCES jogador(id);

ALTER TABLE transacao
    ADD CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES jogador(id);

ALTER TABLE transacao
    ADD CONSTRAINT fk_propriedade FOREIGN KEY (propriedade_id) REFERENCES propriedade(id);

ALTER TABLE ranking
    ADD CONSTRAINT fk_ranking_jogador FOREIGN KEY (jogador_id) REFERENCES jogador(id);

ALTER TABLE ranking
    ADD CONSTRAINT fk_ranking_sala FOREIGN KEY (sala_id) REFERENCES sala(id);

ALTER TABLE emprestimo
    ADD CONSTRAINT fk_jogador_origem FOREIGN KEY (jogador_origem_id) REFERENCES jogador(id);

ALTER TABLE emprestimo
    ADD CONSTRAINT  fk_jogador_destino FOREIGN KEY (jogador_destino_id) REFERENCES jogador(id)
