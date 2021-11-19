CREATE TABLE product_sizes (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    size text,
    chest text,
    height text,
    length text,
    sleeve text,
    shoulder text,
    width text,
    indicated_height text,
    indicated_weight text,
    image text
);