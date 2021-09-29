CREATE TABLE purchase_items (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    stock_id uuid NOT NULL,
    purchase_id uuid NOT NULL,
    quantity integer,
    payment_id text,
    cost decimal,
    available boolean,
    CONSTRAINT fk_stock
        FOREIGN KEY(stock_id)
            REFERENCES stock(id),
    CONSTRAINT fk_purchases
        FOREIGN KEY(purchase_id)
            REFERENCES purchases(id)
);