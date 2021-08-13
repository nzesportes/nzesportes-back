CREATE TABLE categories_sub_categories(
    category_id uuid NOT NULL REFERENCES categories,
    sub_category_id uuid NOT NULL REFERENCES sub_categories,
    PRIMARY KEY (category_id, sub_category_id)
);