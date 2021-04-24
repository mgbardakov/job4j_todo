DROP TABLE IF EXISTS item;
CREATE TABLE item (
    id SERIAL PRIMARY KEY,
	description TEXT,
	created  TIMESTAMP,
	done BOOLEAN
);