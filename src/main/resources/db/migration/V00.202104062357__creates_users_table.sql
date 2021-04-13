CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    username text UNIQUE NOT NULL,
    password text NOT NULL,
    role text NOT NULL
);