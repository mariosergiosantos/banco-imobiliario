-- Criação das tabelas
CREATE TABLE Sala (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    data_criacao TIMESTAMP NOT NULL,
    status ENUM('ABERTA', 'EM_ANDAMENTO', 'ENCERRADA') NOT NULL,
    administrador_id UUID
);

CREATE TABLE Jogador (
    id UUID NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    saldo DOUBLE NOT NULL,
    sala_id VARCHAR(7),
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE Propriedade (
    id UUID NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cor ENUM('ROXO', 'CIANO', 'ROSA', 'LARANJA', 'VERMELHO', 'AMARELO', 'VERDE', 'AZUL'),
    valor_compra DOUBLE NOT NULL,
    aluguel_base DOUBLE NOT NULL,
    numero_casas INT,
    hotel BOOLEAN NOT NULL DEFAULT FALSE,
    valor_aluguel_atual DOUBLE NOT NULL,
    hipotecada BOOLEAN NOT NULL DEFAULT FALSE,
    is_companhia BOOLEAN NOT NULL DEFAULT FALSE,
    dono_id UUID,
    sala_id VARCHAR(7) NOT NULL,
    tipo_propriedade VARCHAR(31) NOT NULL
);

CREATE TABLE Transacao (
    id UUID NOT NULL PRIMARY KEY,
    sala_id VARCHAR(7) NOT NULL,
    origem_id UUID,
    destino_id UUID,
    propriedade_id UUID,
    valor DOUBLE NOT NULL,
    tipo ENUM('COMPRA_PROPRIEDADE', 'PAGAMENTO_ALUGUEL', 'TRANSFERENCIA', 'HIPOTECA', 'COMPRA_DO_BANCO', 'SALARIO') NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    descricao VARCHAR(100)
);

CREATE TABLE Ranking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    jogador_id VARCHAR(50) NOT NULL,
    saldo_final DOUBLE NOT NULL,
    numero_propriedades INT NOT NULL,
    data_vitoria TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sala_id VARCHAR(7) NOT NULL
);


-- Adição das constraints após a criação das tabelas
ALTER TABLE Sala
    ADD CONSTRAINT fk_administrador FOREIGN KEY (administrador_id) REFERENCES Jogador(id);

ALTER TABLE Jogador
    ADD CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES Sala(id);

ALTER TABLE Jogador
    ADD CONSTRAINT uq_nome_sala UNIQUE (nome, sala_id);

ALTER TABLE Propriedade
    ADD CONSTRAINT fk_dono FOREIGN KEY (dono_id) REFERENCES Jogador(id);

ALTER TABLE Propriedade
    ADD CONSTRAINT fk_sala_propriedade FOREIGN KEY (sala_id) REFERENCES Sala(id);

--ALTER TABLE Propriedade
--    ADD CONSTRAINT chk_companhia_valida CHECK (
--        (is_companhia = TRUE AND cor IS NULL AND hotel = FALSE AND numero_casas = 0)
--        OR (is_companhia = FALSE);

ALTER TABLE Transacao
    ADD CONSTRAINT fk_sala_transacao FOREIGN KEY (sala_id) REFERENCES Sala(id);

ALTER TABLE Transacao
    ADD CONSTRAINT fk_origem FOREIGN KEY (origem_id) REFERENCES Jogador(id);

ALTER TABLE Transacao
    ADD CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES Jogador(id);

ALTER TABLE Transacao
    ADD CONSTRAINT fk_propriedade FOREIGN KEY (propriedade_id) REFERENCES Propriedade(id);

ALTER TABLE Ranking
    ADD CONSTRAINT fk_ranking_jogador FOREIGN KEY (jogador_id) REFERENCES Jogador(id);

ALTER TABLE Ranking
    ADD CONSTRAINT fk_ranking_sala FOREIGN KEY (sala_id) REFERENCES Sala(id);
