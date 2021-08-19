CREATE TABLE products_details(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    color text,
    description text,
    price decimal,
    sale_id uuid,
    status boolean,
    product_id uuid,
    CONSTRAINT fk_products
        FOREIGN KEY(product_id)
            REFERENCES products(id),
    CONSTRAINT fk_sales
        FOREIGN KEY(sale_id)
            REFERENCES sales(id)
);