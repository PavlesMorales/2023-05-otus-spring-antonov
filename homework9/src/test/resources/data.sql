INSERT INTO genres (name)
VALUES ('Genre_1'),
       ('Genre_2'),
       ('Genre_3');

insert into authors (first_name, last_name)
values ('AuthorFirstName_1', 'AuthorLastName_1'),
       ('AuthorFirstName_2', 'AuthorLastName_2'),
       ('AuthorFirstName_3', 'AuthorLastName_3');

insert into books (title, author_id, genre_id)
values ('BookTitle_1', 1, 1),
       ('BookTitle_2', 2, 2),
       ('BookTitle_3', 3, 3);

insert into comments (text, book_id)
values ('Comment_1', 1),
       ('Comment_2', 1),
       ('Comment_3', 1),
       ('Comment_4', 1),
       ('Comment_5', 1);