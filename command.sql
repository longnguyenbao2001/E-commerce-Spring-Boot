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
FROM public.product_variant_labels;

SELECT id, "name", unit_price, quantity, product_id, product_variant_label_id
FROM public.product_variants;

delete from public.users;

INSERT INTO users (username, email, first_name, last_name, password, role_id, email_verified)
VALUES 
('user3', 'user1@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 2, true),
('user4', 'user2@gmail.com', 'first_name', 'last_name', '$2a$10$.BeHONljnDAimNUU8GNnBORMqjIEvfHW1Fqg/99vM4cPbSxhko89K', 2, true);


SELECT pv.id AS variant_id,
       STRING_AGG(pval.name, ', ') AS variant_name,
       pv.unit_price AS variant_price
FROM product_variants pv
INNER JOIN product_variants_product_variant_atrribute_values pvpav ON pv.id = pvpav.product_variant_id
INNER JOIN product_variant_atrribute_values pval ON pvpav.product_variant_atrribute_value_id = pval.id
GROUP BY pv.id, pv.unit_price;

