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
    ('VIEW_ACCOUNT', 'Can view account endpoint', 1),
    ('VIEW_CARDS', 'Can view cards endpoint', 2),
    ('VIEW_LOANS', 'Can view loans endpoint', 3),
    ('VIEW_BALANCE', 'Can view balance endpoint', 4);
