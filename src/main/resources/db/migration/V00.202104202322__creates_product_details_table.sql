CREATE TABLE products_details(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    color text,
    size text,
    price decimal,
    niche text,
    brand_id UUID,
    sale_id uuid,
    gender char(1),
    status boolean,
    product_id uuid,
    quantity integer,
    CONSTRAINT fk_products
        FOREIGN KEY(product_id)
            REFERENCES products(id),
    CONSTRAINT fk_brands
        FOREIGN KEY(brand_id)
            REFERENCES brands(id),
    CONSTRAINT fk_sales
        FOREIGN KEY(sale_id)
            REFERENCES sales(id)
);