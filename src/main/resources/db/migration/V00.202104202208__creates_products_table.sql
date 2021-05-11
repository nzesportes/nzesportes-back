CREATE TABLE products (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    description text,
    model text UNIQUE
);