CREATE TABLE sub_categories (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    name text,
    category_id uuid,
    gender text,
    status boolean,
    onMenu boolean
);