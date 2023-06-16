DROP TABLE IF EXISTS users, items, bookings, comments CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name  varchar(250)                        NOT NULL,
    email varchar(100)                        NOT NULL,
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name        varchar(50),
    description varchar(88),
    available   varchar(50),
    owner_id     BIGINT                              NOT NULL,
    request_id   BIGINT,
    CONSTRAINT fk_items_to_users FOREIGN KEY (owner_id) REFERENCES users (id),
    UNIQUE (id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    start   timestamp,
    finish  timestamp,
    item_id BIGINT                              NOT NULL,
    user_id BIGINT                              NOT NULL,
    status  varchar,
    CONSTRAINT fk_bookings_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_bookings_to_items FOREIGN KEY (item_id) REFERENCES items (id),
    UNIQUE (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    description     varchar(88),
    user_id         BIGINT                              NOT NULL,
    created         timestamp,
    CONSTRAINT fk_requests_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    text        VARCHAR(1000),
    item_id     BIGINT                              NOT NULL,
    user_id     BIGINT                              NOT NULL,
    created     timestamp,
    CONSTRAINT items FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id)
);