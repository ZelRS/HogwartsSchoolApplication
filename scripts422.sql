CREATE TABLE car
(
    id       SERIAL PRIMARY KEY,
    brand    VARCHAR,
    model    VARCHAR,
    price    MONEY
);

CREATE TABLE human
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR,
    age     INTEGER CHECK ( age > 18 ),
    license BOOLEAN NOT NULL,
    car_id INTEGER REFERENCES car (id) NOT NULL
);

