CREATE TABLE brands (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    name text,
    status boolean,
    on_menu boolean
);