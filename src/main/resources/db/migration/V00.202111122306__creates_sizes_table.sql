CREATE TABLE sizes (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    type text,
    weight int,
    length int,
    depth int,
    height int
);