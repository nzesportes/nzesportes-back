CREATE TABLE ratings(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    customer_id uuid NOT NULL,
    purchase_id uuid NOT NULL,
    rate int CHECK (rate > 0 AND rate <= 10),
    comment text,
    creation_date date,
    CONSTRAINT fk_customer
        FOREIGN KEY(customer_id)
            REFERENCES customers(id),
    CONSTRAINT fk_purchase
        FOREIGN KEY(purchase_id)
            REFERENCES purchases(id)
);