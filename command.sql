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