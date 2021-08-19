CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE customers (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    name text NOT NULL,
    last_name text NOT NULL,
    user_id uuid NOT NULL UNIQUE,
    instagram text UNIQUE,
    phone text,
    birth_date date,
    cpf varchar(14) UNIQUE NOT NULL,
    gender varchar(8),
    CONSTRAINT fk_users
        FOREIGN KEY(user_id)
            REFERENCES users(id)
);