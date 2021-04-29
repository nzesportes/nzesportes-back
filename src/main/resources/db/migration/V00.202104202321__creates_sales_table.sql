CREATE TABLE sales (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    percentage decimal,
    quantity integer,
    start_date timestamp not null,
    end_date timestamp not null,
    quantity_left integer
);