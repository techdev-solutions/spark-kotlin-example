CREATE TABLE person (
  id         IDENTITY PRIMARY KEY,
  first_name VARCHAR,
  last_name  VARCHAR
);

INSERT INTO person (first_name, last_name) VALUES ('Moritz', 'Schulze');
INSERT INTO person (first_name, last_name) VALUES ('Alexander', 'Hanschke');
INSERT INTO person (first_name, last_name) VALUES ('Adrian', 'Krion');
