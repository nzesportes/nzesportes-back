CREATE TABLE addresses (
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    addressee text,
    cep text,
    street text,
    number text,
    complement text,
    reference text,
    state text,
    city text,
    distinct text,
    phone text,
    costumer_id uuid,
    CONSTRAINT fk_costumer
        FOREIGN KEY(costumer_id)
            REFERENCES customers(id)
);