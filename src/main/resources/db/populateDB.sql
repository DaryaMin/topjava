DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
values ('2023-01-08 04:05:06', 'завтрак', 2000, 100000),
       ('2023-01-08 20:05:06', 'ужин', 500, 100000),
       ('2023-01-08 13:05:06', 'обед', 1000, 100000),
       ('2023-01-08 11:05:06', 'завтрак', 2000, 100001),
       ('2023-01-08 14:05:06', 'обед', 2000, 100001);