CREATE TABLE coupons (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    code text,
    discount decimal,
    quantity integer,
    status boolean,
    start_date date not null,
    end_date date not null,
    quantity_left integer
);
