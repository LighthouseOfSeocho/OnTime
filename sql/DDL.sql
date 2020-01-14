DROP DATABASE lighthouse;

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
	promise_name VARCHAR(100) NOT NULL,
	room_host_id VARCHAR(22) NOT NULL ,
	place_name VARCHAR(30) NOT NULL,
	place_x DOUBLE,
	place_y DOUBLE,
	promise_time DATETIME NOT NULL,
	invitation VARCHAR(255) UNIQUE,
	FOREIGN KEY (room_host_id) REFERENCES lighthouse.USER(user_id)
)DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS lighthouse.USER_PROMISE(
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_id VARCHAR(22),
	promise_id INT,
	FOREIGN KEY (user_id) REFERENCES lighthouse.USER(user_id),
	FOREIGN KEY (promise_id) REFERENCES lighthouse.PROMISE(promise_id)
)DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS lighthouse.INVITATION (
	sender VARCHAR(22) NOT NULL,
	recipient VARCHAR(22) NOT NULL,
	promise_id INT NOT NULL,
	invite_time DATETIME NOT NULL,
	FOREIGN KEY (sender) REFERENCES lighthouse.USER(user_id),
	FOREIGN KEY (recipient) REFERENCES lighthouse.USER(user_id),
	FOREIGN KEY (promise_id) REFERENCES lighthouse.PROMISE(promise_id),
	PRIMARY KEY (sender, recipient, promise_id)
)DEFAULT CHARSET = UTF8;

ALTER TABLE USER_PROMISE ADD CONSTRAINT uq_user_promise UNIQUE (user_id, promise_id);

DELIMITER //
CREATE TRIGGER after_create_promise AFTER INSERT ON lighthouse.promise FOR EACH ROW
BEGIN 
INSERT INTO lighthouse.user_promise(user_id, promise_id) VALUES (new.room_host_id,new.promise_id);
END
//
DELIMITER ;


ALTER TABLE lighthouse.user_promise ADD longitude FLOAT;
ALTER TABLE lighthouse.user_promise ADD latitude FLOAT;
ALTER TABLE lighthouse.user_promise ADD arrival INT;
ALTER TABLE lighthouse.promise ADD address VARCHAR(255);
