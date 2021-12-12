INSERT INTO authority (name) VALUES ('ROLE_MERCHANT');



insert into payment_methods (name, uri)
values ('Credit Card', 'http://localhost:8100/api');

insert into payment_methods (name, uri)
values ('Paypal', 'http://localhost:9000/api');

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




