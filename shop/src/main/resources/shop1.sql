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
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, name, web_site_url, success_url, fail_url, error_url, enabled) VALUES ('ME', 'merchant1@gmail.com', 'Petar', 'Petrovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, 'WinWin', 'https://localhost:8081/#/', 'https://localhost:8081/#/success', 'https://localhost:8081/#/fail', 'https://localhost:8081/#/error', true);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date, name, web_site_url, success_url, fail_url, error_url, enabled) VALUES ('ME', 'merchant2@gmail.com', 'Zarko', 'Stefanovic', '$2a$12$wNTgmVgZyA4gzAYqJOxEiOJPwCZ3SFFsGEGRbTaqphjitHmawdr.m', 1608560339402, 'Gigatron', 'https://localhost:8081/#/', 'https://localhost:8081/#/success', 'https://localhost:8081/#/fail', 'https://localhost:8081/#/error', true);

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

INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id, online) VALUES ('CRS', 'Java Advanced Masterclass', 'Really good lessons', 'Novi Sad', 250, 1641750300000, 1641750400000, 'Milan Marinkovic', 14, true);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id, online) VALUES ('CRS', 'Rust Advanced Masterclass', 'Really good lessons', 'Belgrade', 450, 1641560400000, 1641646800000, 'Jelena Cupac', 14, true);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id, online) VALUES ('CRS', 'Golang Advanced Masterclass', 'Really good lessons', 'Vienna', 550, 1643731200000, 1643832000000, 'Aleksa Goljovic', 15, false);

INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id, online, status) VALUES ('CNF', 'International Conference on Information Systems', 'Really good conference', 'Novi Sad', 70, 1641755160000, 1641755170000, 14, false, 'NOT_FINISHED');
INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id, online, status) VALUES ('CNF', 'International Conference on Machine Learning', 'Really good conference', 'Belgrade', 200, 1641805200000, 1641713400000, 15, true, 'NOT_FINISHED');
INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id, online, status) VALUES ('CNF', 'International Conference on Software Engineering', 'Really good conference', 'London', 900, 1644051600000, 1644483600000, 15, false, 'NOT_FINISHED');
