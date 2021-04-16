CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE profiles (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    name text NOT NULL,
    last_name text NOT NULL,
    user_id uuid NOT NULL UNIQUE,
    instagram text UNIQUE,
    phone text,
    cpf varchar(14),
    CONSTRAINT fk_users
        FOREIGN KEY(user_id)
            REFERENCES users(id)
);