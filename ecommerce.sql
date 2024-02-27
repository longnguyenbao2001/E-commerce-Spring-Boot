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

create table product_images (
	id BIGSERIAL primary key,
	url VARCHAR(2048) not null,
	product_id BIGINT,
	foreign key(product_id) references products(id) on delete cascade
);

create table variants (
	id BIGSERIAL primary key,
	product_id BIGINT,
	unit_price DECIMAL(10, 2) not null,
	quantity INT not null,
	foreign key(product_id) references products(id) on delete cascade
);

create table variant_labels (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null unique
);

create table variant_values (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null, 
	variant_label_id BIGINT,
	foreign key(variant_label_id) references variant_labels(id) on delete cascade
);

create table variants_variant_values (
	variant_id BIGINT,
	variant_value_id BIGINT,
	primary key(variant_id, variant_value_id),
	foreign key(variant_id) references variants(id) on delete cascade,
	foreign key(variant_value_id) references variant_values(id) on delete cascade
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
	variant_id BIGINT,
	PRIMARY KEY (order_id, product_id, variant_id),
	FOREIGN KEY(order_id) REFERENCES orders(id) on delete set null,
	FOREIGN KEY(product_id) REFERENCES products(id) on delete cascade,
	FOREIGN KEY(variant_id) REFERENCES variants(id) on delete cascade
);

INSERT INTO roles (name)
VALUES ('USER'), ('ADMIN');

INSERT INTO users (username, email, first_name, last_name, password, role_id, email_verified)
VALUES 
('username1', 'username1@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1, true), 
('username2', 'username2@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1, true),
('username3', 'username3@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1, true),
('username4', 'username4@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1, true),
('user1', 'user1@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1, true),
('user2', 'user2@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 1, true);

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

INSERT INTO variant_labels (name)
VALUES 
('COLOR'), 
('SIZE'),
('MEMORY');

INSERT INTO variant_values (name, variant_label_id)
VALUES 
('BLUE', 1), ('BLACK', 1), ('YELLOW', 1), --variant for color
('SMALL', 2), ('MEDIUM', 2), ('LARGE', 2), --variant for size
('2GB', 3), ('3GB', 3), ('6GB', 3); --variant for memory

INSERT INTO variants (unit_price, quantity, product_id)
VALUES 
(300000, 10, 1), --create combined variant price and quantity for blue, 2gb, small  variant
(320000, 10, 1), --create combined variant price and quantity for black, 3gb, medium  variant
(280000, 10, 1); --create combined variant price and quantity for yellow, 6gb, large  variant

INSERT INTO variants_variant_values (variant_id, variant_value_id)
VALUES 
(1, 1), (1, 4), (1, 7), --create variant blue, 2gb, small  variant
(2, 2), (2, 5), (2, 8), --create variant black, 3gb, medium  variant
(3, 3), (3, 6), (3, 9); --create variant yellow, 6gb, large  variant

INSERT INTO orders (customer_id)
VALUES (1);

INSERT INTO order_details (order_id, product_id, variant_id, quantity)
VALUES 
(1, 1, 1, 10), 
(1, 1, 2, 5),
(1, 1, 3, 7);
