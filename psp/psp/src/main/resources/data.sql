INSERT INTO roles (name) VALUES ('ROLE_MERCHANT');



insert into payment_methods (name, uri)
values ('Credit Card', 'https://localhost:8080/bank');

insert into payment_methods (name, uri)
values ('Paypal', 'https://localhost:8080/paypal');

insert into payment_methods (name, uri)
values ('Bitcoin', 'https://localhost:8080/bitcoin');



--Credit card
insert into payment_method_attributes (name, "type", payment_method_id)
values ('Merchant Id', 'text', 1);
insert into payment_method_attributes (name, "type", payment_method_id)
values ('Merchant Password', 'password', 1);

--PayPal
insert into payment_method_attributes (name, "type", payment_method_id)
values ('Merchant client Id', 'text', 2);
insert into payment_method_attributes (name, "type", payment_method_id)
values ('Merchant Client Secret', 'password', 2);

--Bitcoin
insert into payment_method_attributes (name, "type", payment_method_id)
values ('Merchant Token', 'password', 3);


--Merchant
INSERT INTO merchants
(email, last_password_reset_date, "password", shop_name, supports_payment_methods, verified)
VALUES('merchant1@gmail.com', 0, '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 'Shop 1', false, true);


