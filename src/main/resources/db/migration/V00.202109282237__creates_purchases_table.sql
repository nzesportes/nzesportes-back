CREATE TABLE purchases(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    customer_id uuid NOT NULL,
    shipment decimal,
    total_cost decimal,
    status text,
    payment_request_id uuid NOT NULL,
    CONSTRAINT fk_stock
        FOREIGN KEY(customer_id)
            REFERENCES customers(id),
    CONSTRAINT fk_payment_requests
        FOREIGN KEY(payment_request_id)
            REFERENCES payment_requests(id)
);