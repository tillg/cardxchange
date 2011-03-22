-- mysql -u root -p cuebid
-- create database cx;


-- User
DROP TABLE IF EXISTS cx_user;
CREATE TABLE cx_user (
	user_id       INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(user_id),
	firstname     VARCHAR(255),
	lastname      VARCHAR(255),
	login         VARCHAR(255) NOT NULL,
	pwd           VARCHAR(255) NOT NULL,
	primary_email VARCHAR(255),
	last_login    DATETIME,
	active        CHAR(1) NOT NULL DEFAULT 'A'
);

CREATE UNIQUE INDEX idx_user ON cx_user (login);

-- Address
DROP TABLE IF EXISTS cx_address;
CREATE TABLE cx_address {
	user_id         INT NOT NULL,
	address_type    CHAR(1) NOT NULL,   -- '1' for business, '2' for private
	street          VARCHAR(255),
	zip             VARCHAR(255),
	city            VARCHAR(255),
	country         VARCHAR(255),
	state           VARCHAR(255),
	tel_country     VARCHAR(255),
	tel_city        VARCHAR(255),
	tel_number      VARCHAR(255),
	fax_country     VARCHAR(255),
	fax_city        VARCHAR(255),
	fax_number      VARCHAR(255),
	mobile_country  VARCHAR(255),
	mobile_city     VARCHAR(255),
	mobile_number   VARCHAR(255)
}
ALTER TABLE cx_address ADD PRIMARY KEY (user_id, address_type);
