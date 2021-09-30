ALTER TABLE purchases ADD shipment_address_id uuid NOT NULL;

ALTER TABLE purchases
ADD CONSTRAINT fk_address
FOREIGN KEY(shipment_address_id)
REFERENCES addresses(id);