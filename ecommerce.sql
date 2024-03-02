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
	description text,
	parent_category_id BIGINT,
	foreign key(parent_category_id) references categories(id) on delete set null
);

create table products (
	id BIGSERIAL primary key,
	name VARCHAR(255) not null, 
	description text not null,
	seller_id BIGINT,
	foreign key(seller_id) references users(id) on delete set null
);

create table products_categories (
	product_id BIGINT,
	category_id BIGINT,
	primary key(product_id, category_id),
	foreign key(product_id) references products(id) on delete cascade,
	foreign key(category_id) references categories(id) on delete cascade
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

INSERT INTO categories (name, description, parent_category_id)
VALUES 
('category 1', 'category description 1', null), 
('category 2', 'category description 2', 1), 
('category 3', 'category description 3', 1),
('category 4', 'category description 4', 1);

INSERT INTO products (name, description, seller_id)
VALUES 
('product 1', 'product description 1', 1), 
('product 2', 'product description 2', 1),
('product 3', 'product description 3', 1),
('product 4', 'product description 4', 1),
('product 5', 'product description 5', 1),
('product 6', 'product description 6', 1),
('product 7', 'product description 7', 1),
('product 8', 'product description 8', 1),
('product 9', 'product description 9', 1),
('product 10', 'product description 10', 1);

INSERT INTO products_categories (product_id, category_id)
VALUES 
(1, 2), 
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 3),
(8, 3),
(9, 3),
(10, 4);

INSERT INTO product_images  (product_id, url)
VALUES 
(1, 'https://picsum.photos/id/2/5000/3333'), 
(2, 'https://picsum.photos/id/2/5000/3333'),
(3, 'https://picsum.photos/id/2/5000/3333'),
(4, 'https://picsum.photos/id/2/5000/3333'),
(5, 'https://picsum.photos/id/2/5000/3333'),
(6, 'https://picsum.photos/id/2/5000/3333'),
(7, 'https://picsum.photos/id/2/5000/3333'),
(8, 'https://picsum.photos/id/2/5000/3333'),
(9, 'https://picsum.photos/id/2/5000/3333'),
(10, 'https://picsum.photos/id/2/5000/3333');

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
(1500000, 10, 1), --create combined variant price and quantity for blue, 2gb, small  variant
(1700000, 10, 1), --create combined variant price and quantity for black, 3gb, medium  variant
(3100000, 10, 1), --create combined variant price and quantity for yellow, 6gb, large  variant
(3200000, 10, 1), --create combined variant price and quantity for black, 6gb, large  variant
(1400000, 10, 2),
(1400000, 10, 3),
(1400000, 10, 4),
(1400000, 10, 5),
(1400000, 10, 6),
(1400000, 10, 7),
(1400000, 10, 8),
(1400000, 10, 9),
(1400000, 10, 10);

INSERT INTO variants_variant_values (variant_id, variant_value_id)
VALUES 
(1, 1), (1, 4), (1, 7), --create variant blue, 2gb, small  variant
(2, 2), (2, 5), (2, 8), --create variant black, 3gb, medium  variant
(3, 3), (3, 6), (3, 9), --create variant yellow, 6gb, large  variant
(4, 2), (4, 6), (4, 9); --create variant black, 6gb, large  variant

INSERT INTO orders (customer_id)
VALUES (1);

INSERT INTO order_details (order_id, product_id, variant_id, quantity)
VALUES 
(1, 1, 1, 10), 
(1, 1, 2, 5),
(1, 1, 3, 7);

update products 
set description = 'Bạn cần một chiếc Laptop để Học tập, Làm việc, hoặc Giải trí, chơi GAME nhẹ. Một số mẫu Laptop dưới đây sẽ đáp ứng được yêu cầu của bạn. Shop AnhVu xin giới thiệu một số mẫu Laptop dựa trên tiêu chí: Giá cả phù hợp, hình thức đẹp, hoạt động ổn định, giúp Quý khách lựa chọn một cách dễ dàng. 
- Quý khách có thể nhờ SHOP tư vấn qua kênh CHAT của Shopee. Nhấn vào MUA HÀNG rồi chọn từng loại cụ thể.
- Shop đã phân loại dựa theo Khung Giá tiền để Quý khách lựa chọn dễ dàng.
- Mọi Laptop bán ra đều được kiểm tra kỹ trước khi giao.

👉 Phù hợp các công việc và giải trí:
- Các công việc văn phòng: word, excel, in ấn văn bản...
- Phù hợp bán hàng online
- Phù hợp học sinh, sinh viên học tập, học Online Zoom, meeting...
- Phù hợp với việc giải trí: xem phim, nghe nhạc, lướt web..
- Chơi game nhẹ nhàng không đòi hỏi card đồ hoạ cao.

👉 Thông số kỹ thuật:
- CPU: Từ pentium, Core 2 Duo, Core i3, Core i5, Core i7 thế hệ 1, 2, 3, 4, 5
- Bộ nhớ Ram: Từ 2Gb - 8Gb tùy loại
- Ổ cứng lưu trữ: HDD 250Gb-500gb, hoặc SSD 120-250G
- Màn hình: 14inch -15.6 inch
- Pin: Đối với Laptop dướ 3Tr Pin đạt trên 60p, Đối với Laptop trên 3Tr Pin đạt trên 2h sử dụng

👉 Sản phẩm gồm có :
- 01 Laptop 
- 01 bộ sạc đi kèm máy
- 01 chuột quang tặng kèm với LAPTOP trên 3tr
- 01 Cặp xách tặng kèm với LAPTOP trên 3tr

👉 Cam kết và bảo hành:
- Cam kết máy nguyên bản, chưa sửa chữa
- Bảo hành 1 tháng. 1 đổi 1 trong 7 ngày đầu tiên
- Hỗ trợ phí dịch vụ sửa chữa( nếu có) sau thời gian bảo hành.

==&gt; Lưu ý:
- Sản phẩm Shop giao ngẫu nhiên nhiều Model của nhiều hãng tùy theo Tồn kho và Giá sản phẩm.
- Quý khách có yêu cầu về Hãng, Model, Màu sắc, Hệ điều hành.... xin vui lòng liên hệ với SHOP hoặc qua kênh CHAT.

▶▶▶ Địa Chỉ Liên Hệ ◀ ◀ ◀
✪ VietThangPC
✪ Hotline:--0972.834.386';
