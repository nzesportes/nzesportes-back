CREATE TABLE product_details_categories(
    product_details_id uuid NOT NULL REFERENCES products_details,
    category_id uuid NOT NULL REFERENCES categories,
    PRIMARY KEY (product_details_id, category_id)
);