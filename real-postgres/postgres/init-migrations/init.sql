-- Собака
--     имя PK
--     координаты

-- Пользователь
--     login PK
--     координаты

-- Выгул
--     собака
--     пользователь
--     координаты
--     время начала
--     продолжительность
--     цена
--     принят ли?

CREATE TABLE IF NOT EXISTS dogs (
   name text PRIMARY KEY not null,
   longitude real not null,
   latitude real not null
);

CREATE TABLE IF NOT EXISTS users (
   login text PRIMARY KEY not null,
   longitude real not null,
   latitude real not null,
   balance real not null default 0.0
);

CREATE TABLE IF NOT EXISTS walks (
   dog_name text not null,
   user_login text not null,
   longitude real not null,
   latitude real not null,
   start_time TIMESTAMP not null,
   duration_minutes bigint not null,
   price_usd real not null,
   is_accepted boolean not null default false,

   PRIMARY KEY(dog_name, user_login, start_time),
   FOREIGN KEY (dog_name) REFERENCES dogs (name),
   FOREIGN KEY (user_login) REFERENCES users (login)
);

insert into dogs(name, longitude, latitude) VALUES('sphere', 1, 2);

insert into dogs(name, longitude, latitude) VALUES('rex', -32, 2);
insert into dogs(name, longitude, latitude) VALUES('shmex', 15, 2234);

insert into users(login, longitude, latitude) VALUES('uwuthless', 6,2);
insert into users(login, longitude, latitude) VALUES('vasya', 36,-2);
insert into users(login, longitude, latitude) VALUES('jenya', 26,16);

insert into walks(dog_name, user_login, longitude, latitude, start_time, duration_minutes, price_usd, is_accepted) VALUES('sphere', 'uwuthless', 5,6, '2022-10-10 11:30:30', 50, 10, false);


BEGIN;
    --rename name column
    alter table public.dogs add column name_clone text default null;
    update public.dogs set name_clone = name;

    --make FK cascading
    ALTER TABLE public.walks DROP CONSTRAINT walks_dog_name_fkey;
    alter table walks
        add constraint walks_dog_name_fkey    foreign key (dog_name)    REFERENCES dogs (name) on update cascade on delete cascade;

    --gen new PK for dogs
    update public.dogs set name = gen_random_uuid();

    --rename columns to reflect new data
    ALTER TABLE dogs RENAME COLUMN name TO id;
    ALTER TABLE dogs RENAME COLUMN name_clone TO name;

    ALTER TABLE walks RENAME COLUMN dog_name TO dog_id;
COMMIT;
insert into dogs(id, name, longitude, latitude) VALUES('3e66ff46-2386-48ec-a6c8-7c1c771dab70', 'bobik', 16, 223);


ALTER TABLE public.dogs ADD COLUMN location point default null;

update public.dogs set location = point('(' || longitude || ', ' || latitude || ')');


CREATE INDEX ON dogs USING GIST(location);

