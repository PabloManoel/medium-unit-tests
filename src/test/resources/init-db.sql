CREATE TABLE db.client (
                           id bigint not null AUTO_INCREMENT primary key,
                           document_type enum('CPF', 'CNPJ'),
                           document varchar(14) unique,
                           first_name varchar(255),
                           last_name varchar(255)
);

CREATE INDEX idx_client_document ON db.client (document);

insert into db.client (document_type, document, first_name, last_name)
values ('CPF', '51694528022', 'Maria', 'One');

insert into db.client (document_type, document, first_name, last_name)
values ('CPF', '34010675080', 'Joao', 'Two');

insert into db.client (document_type, document, first_name, last_name)
values ('CNPJ', '09420103000185', 'Pedro', 'Three');