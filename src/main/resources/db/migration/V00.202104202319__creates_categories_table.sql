CREATE TABLE categories (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    name text,
    status boolean,
    onMenu boolean
);