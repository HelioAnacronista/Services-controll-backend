CREATE TABLE tb_category (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    description varchar(255)
);

CREATE TABLE tb_clients (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    phone varchar(255),
    address varchar(255)
);

CREATE TABLE tb_expense (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    valor double precision
);

CREATE TABLE tb_services (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    id_category bigint REFERENCES tb_category (id),
    id_client bigint REFERENCES tb_clients (id),
    valor double precision
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


INSERT INTO tb_category (name, description) VALUES ('Categoria 1', 'Descrição da categoria 1');
INSERT INTO tb_clients (name, phone, address) VALUES ('Cliente 1', '12345678', 'Endereço do cliente 1');
INSERT INTO tb_expense (name, valor) VALUES ('Despesa 1', 100.50);
INSERT INTO tb_services (name, status, id_category, id_client, valor) VALUES ('Serviço 1', 'Em andamento', 1, 1, 200.00);


INSERT INTO tb_user ( name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$12$Z.XvcAVkasxCPwSGpdOK0Oo4uCAnwOLW/yz88AB5ahShz/hv6v1TS');
INSERT INTO tb_user ( name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$12$ruxbdPyhKlE7mCEiLH5inuTLz3hNOYFRWWJkLNKDA0gniCMIrPOgu');



INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT'); -- 1
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN'); -- 2

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);