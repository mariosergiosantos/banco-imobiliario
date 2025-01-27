ALTER TABLE Emprestimo
ADD COLUMN sala_id VARCHAR(7) NOT NULL;

ALTER TABLE Emprestimo
    ADD CONSTRAINT fk_emprestimo_sala FOREIGN KEY (sala_id) REFERENCES Sala(id);