DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS photo;
DROP TABLE IF EXISTS users;
CREATE TABLE photo (
    id SERIAL PRIMARY KEY
);
CREATE TABLE post (
  id SERIAL PRIMARY KEY,
  name TEXT
);
CREATE TABLE candidate (
  id SERIAL PRIMARY KEY,
  name TEXT,
  photo_id int REFERENCES photo(id) DEFAULT 0
);
INSERT INTO photo (id)
VALUES (0);
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name TEXT,
  email TEXT,
  password TEXT
);