DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    login TEXT,
    password TEXT
);

CREATE TABLE item (
    id SERIAL PRIMARY KEY,
	description TEXT,
	created  TIMESTAMP,
	done BOOLEAN,
	user_id INT,
	CONSTRAINT fk_user
      FOREIGN KEY(user_id)
	  REFERENCES users(id)
);


