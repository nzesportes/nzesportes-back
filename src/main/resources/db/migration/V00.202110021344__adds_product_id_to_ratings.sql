ALTER TABLE ratings ADD product_id uuid;

ALTER TABLE ratings
    ADD CONSTRAINT fk_products
        FOREIGN KEY(product_id)
            REFERENCES products(id);