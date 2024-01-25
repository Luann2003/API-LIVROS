INSERT INTO tb_user (name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Luan Victor', 'verfute2005@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');

INSERT INTO tb_role (authority) VALUES ('ROLE_MEMBER');
INSERT INTO tb_role (authority) VALUES ('ROLE_RESPONSIBLE');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);

INSERT INTO tb_author(name) VALUES ('José Saramago');
INSERT INTO tb_author(name) VALUES ('Clarice Lispector');
INSERT INTO tb_author(name) VALUES ('Edgar Allan Poe');
INSERT INTO tb_author(name) VALUES ('Fiódor Dostoiévski');
INSERT INTO tb_author(name) VALUES ('William Shakespeare');
INSERT INTO tb_author(name) VALUES ('Marcel Proust');
INSERT INTO tb_author(name) VALUES ('Miguel de Cervantes');
INSERT INTO tb_author(name) VALUES ('Gabriel García Márquez');
INSERT INTO tb_author(name) VALUES ('Franz Kafka');
INSERT INTO tb_author(name) VALUES ('Jorge Luis Borges');

INSERT INTO tb_publisher(name) VALUES ('Seix Barral');
INSERT INTO tb_publisher(name) VALUES ('Editora Rocco');
INSERT INTO tb_publisher(name) VALUES ('');
INSERT INTO tb_publisher(name) VALUES ('');
INSERT INTO tb_publisher(name) VALUES ('');
INSERT INTO tb_publisher(name) VALUES ('Gallimard');
INSERT INTO tb_publisher(name) VALUES ('Editorial Sudamericana');
INSERT INTO tb_publisher(name) VALUES ('');
INSERT INTO tb_publisher(name) VALUES ('');
INSERT INTO tb_publisher(name) VALUES ('');


INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id) VALUES ('Ensaio sobre a Cegueira', 9783127323207, 1995, 1, 1);






