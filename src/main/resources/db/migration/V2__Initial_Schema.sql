ALTER TABLE propriedade
    DROP COLUMN aluguel_base,
    DROP COLUMN valor_aluguel_atual;

CREATE TABLE aluguel_propriedade (
    id CHAR(36) NOT NULL PRIMARY KEY,
    propriedade_id CHAR(36) NOT NULL,
    tipo_aluguel ENUM('BASE', 'CASA_1', 'CASA_2', 'CASA_3', 'CASA_4', 'HOTEL') NOT NULL,
    valor DOUBLE NOT NULL
);

ALTER TABLE aluguel_propriedade
    ADD CONSTRAINT  fk_aluguel_propriedade FOREIGN KEY (propriedade_id) REFERENCES propriedade(id);