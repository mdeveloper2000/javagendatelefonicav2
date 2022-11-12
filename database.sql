CREATE TABLE contatos (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    telefone VARCHAR(14) NOT NULL,
    email VARCHAR(40) NOT NULL,
    foto MEDIUMBLOB,
    anotacoes VARCHAR(150),
    PRIMARY KEY(id),
    UNIQUE(nome)
);