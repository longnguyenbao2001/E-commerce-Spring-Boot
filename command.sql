SELECT id, "name"
FROM public.roles;

SELECT id, username, "password", email, first_name, last_name, role_id
FROM public.users;

SELECT id, "name", description
FROM public.categories;

SELECT id, "name", description, price, quantity, seller_id, category_id
FROM public.products;

delete from public.users;