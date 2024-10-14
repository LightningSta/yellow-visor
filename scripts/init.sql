-- Подключаемся к базе данных survey_bot
\c dash;
create table Person(
    id serial primary key,
    login varchar not null,
    password varchar not null,
    nick varchar not null,
    tg varchar,
    email varchar not null,
    role varchar not null,
    projects varchar
);
 create table Groupp(
                      id serial primary key,
                      group_name varchar
);
 create table GroupPersons(
                             group_id INT REFERENCES groupp(id),
                             person_id INT REFERENCES person(id),
                             PRIMARY KEY (group_id, person_id)
);

-- Меняем владельца таблицы на bot_user
