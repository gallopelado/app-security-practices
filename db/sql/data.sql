-----------------data------------------
INSERT INTO
    customers (email, pwd)
VALUES
    ('account@email.com', '1'),
    ('cards@email.com', '2'),
    ('loans@email.com', '3'),
    ('balance@email.com', '4');
--
INSERT INTO
    roles (role_name, description, id_customer)
VALUES
    ('ROLE_ADMIN', 'Can view account endpoint', 1),
    ('ROLE_ADMIN', 'Can view cards endpoint', 2),
    ('ROLE_USER', 'Can view loans endpoint', 3),
    ('ROLE_USER', 'Can view balance endpoint', 4);
