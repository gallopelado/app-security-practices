-----------data-------------

insert into users (username, password, enabled) VALUES
                                                    ('admin', '1', true),
                                                    ('user', '2', true);

insert into authorities (username, authority) VALUES
                                                  ('admin', 'admin'),
                                                  ('user', 'user');