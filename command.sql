SELECT id, "name"
FROM public.roles;

SELECT id, username, "password", email, first_name, last_name, role_id, email_verified
FROM public.users;

SELECT id, address_line_1, address_line_2, city, country, phone_number, user_id
FROM public.ship_addresses;

SELECT id, "token", created_timestamp, user_id
FROM public.verification_tokens;

SELECT id, "name", description
FROM public.categories;

SELECT id, "name", description, seller_id, category_id
FROM public.products;

SELECT id, "name"
FROM public.variant_labels;

SELECT id, "name", variant_label_id
FROM public.variant_values;

SELECT id, product_id, unit_price, quantity
FROM public.variants;

SELECT variant_id, variant_value_id
FROM public.variants_variant_values;

SELECT quantity, order_id, product_id, variant_id
FROM public.order_details;

SELECT id, order_date, customer_id
FROM public.orders;

SELECT quantity, order_id, product_id, variant_id
FROM public.order_details;

delete from public.users;

INSERT INTO users (username, email, first_name, last_name, password, role_id, email_verified)
VALUES 
('user4', 'user1@gmail.com', 'first_name', 
'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 2, true);

INSERT INTO order_details (order_id, product_id, variant_id, quantity)
VALUES 
(1, 2, 2, 10);
