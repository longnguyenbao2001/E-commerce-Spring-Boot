SELECT id, "name"
FROM public.roles;

SELECT id, username, "password", email, first_name, last_name, role_id, email_verified
FROM public.users;

SELECT id, "token", created_timestamp, user_id
FROM public.verification_tokens;

SELECT id, "name", description
FROM public.categories;

SELECT id, "name", description, price, quantity, seller_id, category_id
FROM public.products;

delete from public.users;