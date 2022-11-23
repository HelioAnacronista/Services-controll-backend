INSERT INTO tb_user (name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$12$Z.XvcAVkasxCPwSGpdOK0Oo4uCAnwOLW/yz88AB5ahShz/hv6v1TS');
INSERT INTO tb_user (name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$12$Z.XvcAVkasxCPwSGpdOK0Oo4uCAnwOLW/yz88AB5ahShz/hv6v1TS');


INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT'); -- 1
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN'); -- 2

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);