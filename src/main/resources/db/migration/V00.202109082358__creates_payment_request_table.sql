CREATE TABLE payment_requests (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    buyerId uuid NOT NULL,
    webhookStatus text;
    creation_date date,
    update_date date,
    confirmation_date date,
    cancellation_date date
);