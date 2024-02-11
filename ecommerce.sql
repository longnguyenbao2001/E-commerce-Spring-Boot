CREATE DATABASE ecommerce;

CREATE TABLE roles (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
	id BIGSERIAL PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE, 
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(255) NOT null,
	email_verified BOOLEAN NOT null DEFAULT false,
	role_id BIGINT,
	foreign key(role_id) references roles(id) on delete set null
);

CREATE TABLE ship_addresses (
	id BIGSERIAL PRIMARY KEY,
	address_line_1 VARCHAR(500) NOT NULL,
	address_line_2 VARCHAR(500),
	city VARCHAR(255) NOT NULL,
	country VARCHAR(255) NOT NULL,
	phone_number VARCHAR(10) NOT NULL,
	user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade
);


CREATE TABLE verification_tokens (
    id BIGSERIAL PRIMARY KEY,
    token TEXT NOT NULL UNIQUE,
    created_timestamp TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade
);

CREATE TABLE categories (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL, 
	description TEXT
);

create table products (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null, 
	description text not null,
	seller_id BIGINT,
	category_id BIGINT,
	foreign key(seller_id) references users(id) on delete set null,
	foreign key(category_id) references categories(id) on delete set null
);

create table product_variant_labels (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null unique
);

create table product_variants (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null, 
	unit_price DECIMAL(10, 2) not null,
	quantity INT not null,
	product_id BIGINT,
	product_variant_label_id BIGINT,
	foreign key(product_id) references products(id) on delete cascade,
	foreign key(product_variant_label_id) references product_variant_labels(id) on delete cascade
);


CREATE TABLE orders (
	id BIGSERIAL PRIMARY KEY,
	order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	customer_id BIGINT,
	FOREIGN KEY(customer_id) REFERENCES users(id) on delete set null
);

CREATE TABLE order_details (
	quantity INT NOT NULL,
	order_id BIGINT,
	product_id BIGINT,
	PRIMARY KEY (order_id, product_id),
	FOREIGN KEY(order_id) REFERENCES orders(id) on delete set null,
	FOREIGN KEY(product_id) REFERENCES products(id) on delete set null
);

INSERT INTO roles (name)
VALUES ('USER'), ('ADMIN');

INSERT INTO users (username, email, first_name, last_name, password, role_id)
VALUES 
('username1', 'username1@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1), 
('username2', 'username2@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1),
('username3', 'username3@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1),
('username4', 'username4@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1),
('user1', 'user1@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1),
('user2', 'user2@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1);

INSERT INTO categories (name, description)
VALUES 
('category 1', 'category description 1'), 
('category 2', 'category description 2'), 
('category 3', 'category description 3');

INSERT INTO products (name, description, seller_id, category_id)
VALUES 
('product 1', 'product description 1', 1, 1), 
('product 2', 'product description 2', 1, 1),
('product 3', 'product description 3', 1, 1),
('product 4', 'product description 4', 1, 1),
('product 5', 'product description 5', 1, 1),
('product 6', 'product description 6', 1, 1),
('product 7', 'product description 7', 1, 1),
('product 8', 'product description 8', 1, 1),
('product 9', 'product description 9', 1, 1),
('product 10', 'product description 10', 1, 1);

INSERT INTO product_variant_labels (name)
VALUES 
('COLOR'), 
('SIZE'),
('MEMORY');

INSERT INTO product_variants (name, unit_price, quantity, product_id, product_variant_label_id)
VALUES 
('BLUE', 3000000, 10, 1, 1), 
('BLACK', 3000000, 10, 1, 1),
('YELLOW', 3000000, 10, 1, 1);

INSERT INTO orders (customer_id)
VALUES (1);

INSERT INTO order_details (order_id, product_id, quantity)
VALUES 
(1, 1, 10), 
(1, 2, 5),
(1, 3, 7);
