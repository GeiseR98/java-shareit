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
    name        varchar(100),
    description varchar(200),
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
    status  varchar(200),
    CONSTRAINT fk_booking_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_booking_to_items FOREIGN KEY (item_id) REFERENCES items (id),
    UNIQUE (id)
);