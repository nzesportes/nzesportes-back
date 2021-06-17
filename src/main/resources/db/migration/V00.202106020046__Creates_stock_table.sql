CREATE TABLE stock (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    product_detail_id uuid,
    size text,
    quantity integer
);