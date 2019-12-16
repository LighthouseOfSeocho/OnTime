CREATE DATABASE lighthouse;

USE lighthouse;

CREATE TABLE IF NOT EXISTS lighthouse.USER (
	user_id VARCHAR(22) PRIMARY KEY,
	user_email VARCHAR(200),
	user_name VARCHAR(30) NOT NULL,
	user_gender VARCHAR(2),
	user_age VARCHAR(160),
	user_account VARCHAR(200),
	user_birthday VARCHAR(20),
	user_phone VARCHAR(26)
)DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS lighthouse.PROMISE (
	promise_id INT PRIMARY KEY AUTO_INCREMENT,
	place_name VARCHAR(30) NOT NULL,
	place_x DOUBLE,
	place_y DOUBLE,
	promise_time DATETIME NOT NULL,
	amount INT
)DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS lighthouse.user_prom_mapping(
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_id VARCHAR(22),
	promise_id INT,
	FOREIGN KEY (user_id) REFERENCES lighthouse.USER(user_id),
	FOREIGN KEY (promise_id) REFERENCES lighthouse.PROMISE(promise_id)
)DEFAULT CHARSET = UTF8;
