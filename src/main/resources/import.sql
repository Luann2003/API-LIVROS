INSERT INTO tb_user (name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Luan Victor', 'verfute2005@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Thiago Ferreira', 'thiago@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');


INSERT INTO tb_role (authority) VALUES ('ROLE_MEMBER');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (4, 2);

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
INSERT INTO tb_author(name) VALUES ('Teste Delete');


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
INSERT INTO tb_publisher(name) VALUES ('Teste Delete');

INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent ) VALUES ('Ensaio sobre a Cegueira', 9783127323207, 1995, 5, 1, 0);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent ) VALUES ('A Hora da Estrela', 9783127323207, 1977, 4, 2, 1);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('The Raven" (O Corvo)', 9783127323207, 1845, 3, 3, 0);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('Crime e Castigo', 9783127323207, 1866, 2, 4, 1);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('Romeu e Julieta', 9783127323207, 1597, 1, 5, 0);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('Em Busca do Tempo Perdido', 9783127323207, 1913, 10, 6, 1);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('Dom Quixote', 9783127323207, 1605, 9, 7, 0);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('Cem Anos de Solidão', 9783127323207, 1967, 8, 8, 0);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('A Metamorfose', 9783127323207, 1915, 7, 9, 0);
INSERT INTO tb_book (title, isbn, year_publication, publisher_id, author_id, rent) VALUES ('Ficções', 9783127323207, 1944, 6, 10, 0);

INSERT INTO tb_rent (price, init_date, devolution_date, expected_return_date, user_id, book_id, devolution) VALUES (20.0, TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2022-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2022-07-23T20:50:07.12345Z',1, 2, 0);
INSERT INTO tb_rent (price, init_date, devolution_date, expected_return_date, user_id, book_id, devolution) VALUES (40.0, TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2022-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2022-07-23T20:50:07.12345Z',2, 4, 0);
INSERT INTO tb_rent (price, init_date, devolution_date, expected_return_date, user_id, book_id, devolution) VALUES (35.20, TIMESTAMP WITH TIME ZONE '2022-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2022-07-20T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2022-07-23T20:50:07.12345Z', 3, 6, 0);




