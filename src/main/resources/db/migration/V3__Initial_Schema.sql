ALTER TABLE sala
    DROP FOREIGN KEY fk_administrador;

ALTER TABLE jogador
    DROP FOREIGN KEY fk_sala;

ALTER TABLE sala
    ADD CONSTRAINT fk_administrador FOREIGN KEY (administrador_id) REFERENCES jogador(id)
     ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE jogador
    ADD CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES sala(id)
     ON DELETE CASCADE ON UPDATE CASCADE;