CREATE TABLE product_details_sub_categories(
    product_details_id uuid NOT NULL REFERENCES products_details,
    sub_category_id uuid NOT NULL REFERENCES sub_categories,
    PRIMARY KEY (product_details_id, sub_category_id)
);