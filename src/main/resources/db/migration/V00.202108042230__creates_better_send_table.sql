CREATE TABLE better_send(
    code text PRIMARY KEY NOT NULL,
    token_type text,
    expires_in int,
    access_token text,
    refresh_token text,
    creation_date date
);