create table tb_categories(
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	description CHARACTER VARYING(240) NOT NULL
);

create table tb_clients (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	phone CHARACTER VARYING(15) NOT NULL,
	address CHARACTER VARYING(240) NOT NULL
);

create table tb_role (
	id SERIAL PRIMARY KEY,
	authority TEXT NOT NULL
);

create table tb_user (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	email TEXT NOT NULL,
	password TEXT NOT NULL
);

create table tb_user_role (
	user_id INT REFERENCES tb_user(id) NOT NULL,
	role_id INT REFERENCES tb_role(id) NOT NULL
);


create table tb_services (
	id SERIAL PRIMARY KEY,
	titulo TEXT NOT NULL,
	descricao TEXT NOT NULL,
	data_Abertura DATE NOT NULL,
	data_Fechamento DATE NOT NULL,
	id_categorys INT REFERENCES tb_categories(id) NOT NULL,
	client_id INT REFERENCES tb_clients(id) NOT NULL,
	nomeCliente TEXT NOT NULL,
	nomeCategoria TEXT NOT NULL,
	valor MONEY NOT NULL
);

<<<<<<< HEAD
INSERT INTO tb_user (name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$12$Z.XvcAVkasxCPwSGpdOK0Oo4uCAnwOLW/yz88AB5ahShz/hv6v1TS');
INSERT INTO tb_user (name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$12$Z.XvcAVkasxCPwSGpdOK0Oo4uCAnwOLW/yz88AB5ahShz/hv6v1TS');
=======
INSERT INTO tb_user ( name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$12$Z.XvcAVkasxCPwSGpdOK0Oo4uCAnwOLW/yz88AB5ahShz/hv6v1TS');
INSERT INTO tb_user ( name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$12$ruxbdPyhKlE7mCEiLH5inuTLz3hNOYFRWWJkLNKDA0gniCMIrPOgu');
>>>>>>> 81bfcf7 (add database)


INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT'); -- 1
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN'); -- 2

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
<<<<<<< HEAD
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
=======
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);


select * from tb_role;

select * from tb_clients;
select * from tb_user;
>>>>>>> 81bfcf7 (add database)
