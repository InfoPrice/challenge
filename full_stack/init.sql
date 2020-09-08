
-- Drop table

-- DROP TABLE public.cidade;

CREATE TABLE public.cidade (
	id serial NOT NULL,
	estado int4 NOT NULL,
	nome varchar(100) NOT NULL,
	CONSTRAINT newtable_pk PRIMARY KEY (id)
);

-- Drop table

-- DROP TABLE public.pessoa;

CREATE TABLE public.pessoa (
	id serial NOT NULL,
	cpf varchar(255) NULL,
	email varchar(255) NULL,
	nome varchar(255) NULL,
	CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

-- Drop table

-- DROP TABLE public.endereco;

CREATE TABLE public.endereco (
	id serial NOT NULL,
	logradouro varchar(100) NOT NULL,
	numero int4 NULL,
	complemento varchar(100) NULL,
	bairro varchar(100) NOT NULL,
	cidade_id int4 NOT NULL,
	cep varchar(11) NOT NULL,
	pessoa_id int4 NOT NULL,
	CONSTRAINT endereco_pk PRIMARY KEY (id),
	CONSTRAINT endereco_fk FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
	CONSTRAINT endereco_fk2 FOREIGN KEY (cidade_id) REFERENCES cidade(id)
);
