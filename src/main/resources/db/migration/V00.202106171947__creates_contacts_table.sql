CREATE TABLE contacts(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    full_name text,
    email text,
    phone text,
    instagram text,
    message text,
    read boolean
);