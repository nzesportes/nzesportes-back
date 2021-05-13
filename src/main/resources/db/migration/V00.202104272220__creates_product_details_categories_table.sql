CREATE TABLE product_categories(
    product_id uuid NOT NULL REFERENCES products,
    category_id uuid NOT NULL REFERENCES categories,
    PRIMARY KEY (product_id, category_id)
);