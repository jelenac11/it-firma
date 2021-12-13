INSERT INTO authority (name) VALUES ('ROLE_MERCHANT');



insert into payment_methods (name, uri)
values ('Credit Card', 'http://localhost:8100/api');

insert into payment_methods (name, uri)
values ('Paypal', 'http://localhost:8080/paypal/api/payment/create-url');

insert into payment_methods (name, uri)
values ('Bitcoin', 'http://localhost:8300/api');



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
values ('Merchant API Token', 'password', 3);


--Merchant
INSERT INTO merchants
(email, error_url, failed_url, last_password_reset_date, "password", shop_name, success_url, supports_payment_methods, verified, web_site_url)
VALUES('merchant1@gmail.com', 'http://localhost:8081/#/fail', 'http://localhost:8081/#/cancel', 0, '$2a$12$AG2MxGN5ClxbU/a./ydhEeN483inOKkCy/GltpwPhqovplZiWPxqS', 'Shop 1', 'http://localhost:8081/#/success', false, true, 'http://localhost:8081/#/equipment');


