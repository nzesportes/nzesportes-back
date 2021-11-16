CREATE TABLE purchase_code (
    id BIGSERIAl PRIMARY KEY,
    purchase_id uuid NOT NULL UNIQUE
);
