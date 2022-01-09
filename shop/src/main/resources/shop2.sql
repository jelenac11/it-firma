INSERT INTO roles (name) VALUES ('ROLE_EQUIPMENT_BUYER');
INSERT INTO roles (name) VALUES ('ROLE_SERVICE_BUYER');
INSERT INTO roles (name) VALUES ('ROLE_MERCHANT');

INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('EB', 'chief1@gmail.com', 'Chief', 'Chiefovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('EB', 'chief2@gmail.com', 'Laza', 'Lazic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('EB', 'chief3@gmail.com', 'Pera', 'Peric', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice1@gmail.com', 'Milica', 'Jovanovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice2@gmail.com', 'Milan', 'Milinkovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice3@gmail.com', 'Jovan', 'Memedovicc', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice4@gmail.com', 'Dejan', 'Djuric', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice5@gmail.com', 'Sasa', 'Lazarevic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice6@gmail.com', 'David', 'Davidovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice7@gmail.com', 'Miladin', 'Milenkovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice8@gmail.com', 'Jelena', 'Nastasic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice9@gmail.com', 'Lenka', 'Dundjerski', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, enabled) VALUES ('SB', 'generalservice10@gmail.com', 'Jasmina', 'Brundic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, name, web_site_url, success_url, fail_url, error_url, enabled) VALUES ('ME', 'merchant3@gmail.com', 'Aleksa', 'Uskokovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, 'WinWin', 'http://localhost:8080/#/', 'http://localhost:8080/#/success', 'http://localhost:8080/#/fail', 'http://localhost:8080/#/error', true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, name, web_site_url, success_url, fail_url, error_url, enabled) VALUES ('ME', 'merchant4@gmail.com', 'Nikola', 'Ivanovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, 'Gigatron', 'http://localhost:8080/#/', 'http://localhost:8080/#/success', 'http://localhost:8080/#/fail', 'http://localhost:8080/#/error', true);

INSERT INTO user_roles(user_id, role_id) VALUES (1,1);
INSERT INTO user_roles(user_id, role_id) VALUES (2,1);
INSERT INTO user_roles(user_id, role_id) VALUES (3,1);

INSERT INTO user_roles(user_id, role_id) VALUES (4,2);
INSERT INTO user_roles(user_id, role_id) VALUES (5,2);
INSERT INTO user_roles(user_id, role_id) VALUES (6,2);
INSERT INTO user_roles(user_id, role_id) VALUES (7,2);
INSERT INTO user_roles(user_id, role_id) VALUES (8,2);
INSERT INTO user_roles(user_id, role_id) VALUES (9,2);
INSERT INTO user_roles(user_id, role_id) VALUES (10,2);
INSERT INTO user_roles(user_id, role_id) VALUES (11,2);
INSERT INTO user_roles(user_id, role_id) VALUES (12,2);
INSERT INTO user_roles(user_id, role_id) VALUES (13,2);

INSERT INTO user_roles(user_id, role_id) VALUES (14,3);
INSERT INTO user_roles(user_id, role_id) VALUES (15,3);

INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('ESC',0,1);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('ESC',0,2);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('ESC',0,3);

INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,4);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,5);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,6);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,7);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,8);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,9);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,10);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,11);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,12);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('SSC',0,13);

INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Mouse1', 'Really cute mouse', 50, 19.99, 'IO_DEVICE', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Printer1', 'Really interesting printer, prints everything', 100, 150, 'IO_DEVICE', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop dell', 'Really cute laptop', 100, 3500, 'LAPTOP', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop apple', 'Really cute laptop', 100, 3699, 'LAPTOP', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop hp', 'Really cute laptop', 120, 2500, 'LAPTOP', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop lenovo', 'Really cute laptop', 120, 299, 'LAPTOP', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Latpop asus', 'Really cute laptop', 120, 2000, 'LAPTOP', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Headphones1', 'Really cute headphones', 50, 50, 'IO_DEVICE', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Headdphones2', 'Really cute headphones', 50, 65, 'IO_DEVICE', 15);

INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id, online) VALUES ('CRS', 'Java Advanced Masterclass', 'Really good lessons', 'Novi Sad', 250, 1641142800000, 1641402000000, 'Milan Marinkovic', 14, true);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id, online) VALUES ('CRS', 'Rust Advanced Masterclass', 'Really good lessons', 'Belgrade', 450, 1641560400000, 1641646800000, 'Jelena Cupac', 14, true);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id, online) VALUES ('CRS', 'Golang Advanced Masterclass', 'Really good lessons', 'Vienna', 550, 1643731200000, 1643832000000, 'Aleksa Goljovic', 15, false);

INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id, online) VALUES ('CNF', 'International Conference on Information Systems', 'Really good conference', 'Novi Sad', 70, 1641646800000, 1641819600000, 14, false);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id, online) VALUES ('CNF', 'International Conference on Machine Learning', 'Really good conference', 'Belgrade', 200, 1641805200000, 1641830400000, 15, true);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id, online) VALUES ('CNF', 'International Conference on Software Engineering', 'Really good conference', 'London', 900, 1644051600000, 1644483600000, 15, false);

INSERT INTO transports ("type", price, location) VALUES ('BUS', 20, 'Belgrade');
INSERT INTO transports ("type", price, location) VALUES ('TRAIN', 10, 'Belgrade');
INSERT INTO transports ("type", price, location) VALUES ('PLANE', 50, 'Belgrade');

INSERT INTO transports ("type", price, location) VALUES ('BUS', 15, 'Novi Sad');
INSERT INTO transports ("type", price, location) VALUES ('TRAIN', 5, 'Novi Sad');
INSERT INTO transports ("type", price, location) VALUES ('PLANE', 45, 'Novi Sad');

INSERT INTO transports ("type", price, location) VALUES ('BUS', 40, 'Vienna');
INSERT INTO transports ("type", price, location) VALUES ('TRAIN', 30, 'Vienna');
INSERT INTO transports ("type", price, location) VALUES ('PLANE', 70, 'Vienna');

INSERT INTO accommodations ("type", price, location) VALUES ('HOTEL', 25, 'Belgrade');
INSERT INTO accommodations ("type", price, location) VALUES ('MOTEL', 15, 'Belgrade');
INSERT INTO accommodations ("type", price, location) VALUES ('APARTMENT', 50, 'Belgrade');

INSERT INTO accommodations ("type", price, location) VALUES ('HOTEL', 20, 'Novi Sad');
INSERT INTO accommodations ("type", price, location) VALUES ('MOTEL', 10, 'Novi Sad');
INSERT INTO accommodations ("type", price, location) VALUES ('APARTMENT', 45, 'Novi Sad');

INSERT INTO accommodations ("type", price, location) VALUES ('HOTEL', 65, 'Vienna');
INSERT INTO accommodations ("type", price, location) VALUES ('MOTEL', 55, 'Vienna');
INSERT INTO accommodations ("type", price, location) VALUES ('APARTMENT', 90, 'Vienna');