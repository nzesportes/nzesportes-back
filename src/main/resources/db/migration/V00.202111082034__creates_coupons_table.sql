CREATE TABLE coupons (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    code text,
    discount decimal,
    quantity integer,
    status boolean,
    start_date timestamp not null,
    end_date timestamp not null,
    quantity_left integer
);
