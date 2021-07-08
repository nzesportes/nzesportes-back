CREATE TABLE recovery_request(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    user_id uuid NOT NULL,
    type text,
    status text;
);