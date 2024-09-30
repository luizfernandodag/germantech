create table usuarios
(
id SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
telefone VARCHAR(20),
email VARCHAR(100),
cpf VARCHAR(11) UNIQUE NOT NULL
)