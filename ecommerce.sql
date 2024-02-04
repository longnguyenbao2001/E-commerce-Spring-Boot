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
	role_id BIGINT,
	foreign key(role_id) references roles(id) on delete set null
);

CREATE TABLE categories (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL, 
	description TEXT NOT NULL
);

create table products (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null, 
	description text not null,
	price DECIMAL(10, 2) not null,
	quantity INT not null,
	seller_id BIGINT,
	category_id BIGINT,
	foreign key(seller_id) references users(id) on delete set null,
	foreign key(category_id) references categories(id) on delete set null
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
