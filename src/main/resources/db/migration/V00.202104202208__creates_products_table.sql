CREATE TABLE products (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    model text UNIQUE,
    brand_id UUID,
    CONSTRAINT fk_brands
        FOREIGN KEY(brand_id)
            REFERENCES brands(id),
    status boolean
);