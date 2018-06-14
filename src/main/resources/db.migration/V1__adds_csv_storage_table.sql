DROP TABLE IF EXISTS csv_storage;

CREATE TABLE csv_storage(
  name VARCHAR(100) NOT NULL,
  file LONGBLOB NOT NULL,

  PRIMARY KEY (name)
);