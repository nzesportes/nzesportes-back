CREATE TABLE products_details(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    color text,
    description text,
    price decimal,
    status boolean,
    product_id uuid,
    CONSTRAINT fk_products
        FOREIGN KEY(product_id)
            REFERENCES products(id)
);