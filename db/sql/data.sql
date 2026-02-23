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
    ('ROLE_ACCOUNT', 'Can view account endpoint', 1),
    ('ROLE_CARDS', 'Can view cards endpoint', 2),
    ('ROLE_LOANS', 'Can view loans endpoint', 3),
    ('ROLE_BALANCE', 'Can view balance endpoint', 4);
