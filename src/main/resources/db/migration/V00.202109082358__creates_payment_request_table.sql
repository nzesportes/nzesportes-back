CREATE TABLE payment_requests (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    buyer_id uuid NOT NULL,
    webhook_status text,
    payment_id text,
    creation_date date,
    update_date date,
    confirmation_date date,
    cancellation_date date
);