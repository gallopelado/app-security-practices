----------------schema-----------------
/*
create table customers(
                          id bigserial primary key,
                          email varchar(50) not null,
                          pwd varchar(500) not null,
                          rol varchar(20) not null);
*/
create table customers(
    id bigserial primary key,
    email varchar(50) not null,
    pwd varchar(500) not null
);

CREATE TABLE roles(
    id bigserial PRIMARY KEY,
    role_name VARCHAR(50),
    description VARCHAR(100),
    id_customer bigint,
    CONSTRAINT fk_customer FOREIGN KEY (id_customer) REFERENCES customers(id)
);