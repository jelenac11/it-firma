INSERT INTO authority (name) VALUES ('ROLE_CHIEF');
INSERT INTO authority (name) VALUES ('ROLE_GENERAL_SERVICE_WORKER');
INSERT INTO authority (name) VALUES ('ROLE_MERCHANT');

INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('CH', 'chief1@gmail.com', 'Chief', 'Chiefovic', '$2a$12$AG2MxGN5ClxbU/a./ydhEeN483inOKkCy/GltpwPhqovplZiWPxqS', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('CH', 'chief2@gmail.com', 'Laza', 'Lazic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('CH', 'chief3@gmail.com', 'Pera', 'Peric', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice1@gmail.com', 'Milica', 'Jovanovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice2@gmail.com', 'Milan', 'Milinkovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice3@gmail.com', 'Jovan', 'Memedovicc', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice4@gmail.com', 'Dejan', 'Djuric', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice5@gmail.com', 'Sasa', 'Lazarevic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice6@gmail.com', 'David', 'Davidovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice7@gmail.com', 'Miladin', 'Milenkovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice8@gmail.com', 'Jelena', 'Nastasic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice9@gmail.com', 'Lenka', 'Dundjerski', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('GS', 'generalservice10@gmail.com', 'Jasmina', 'Brundic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('ME', 'merchant1@gmail.com', 'Petar', 'Petrovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);
INSERT INTO users ("type", email, first_name, last_name, "password", last_password_reset_date) VALUES ('ME', 'merchant2@gmail.com', 'Zarko', 'Stefanovic', '$2a$10$pW9Eee2ptMXCv41gjTsFrujLAo6WTRI8LcfA1/qHtyTU5Gn2xHoym', 1608560339402);

INSERT INTO user_authority(user_id, authority_id) VALUES (1,1);
INSERT INTO user_authority(user_id, authority_id) VALUES (2,1);
INSERT INTO user_authority(user_id, authority_id) VALUES (3,1);

INSERT INTO user_authority(user_id, authority_id) VALUES (4,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (5,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (6,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (7,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (8,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (9,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (10,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (11,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (12,2);
INSERT INTO user_authority(user_id, authority_id) VALUES (13,2);

INSERT INTO user_authority(user_id, authority_id) VALUES (14,3);
INSERT INTO user_authority(user_id, authority_id) VALUES (15,3);

INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('CSc',0,1);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('CSc',0,2);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('CSc',0,3);

INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,4);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,5);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,6);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,7);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,8);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,9);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,10);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,11);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,12);
INSERT INTO shopping_carts("type", total_price, user_id) VALUES ('GSSc',0,13);

INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Mouse1', 'Really cute mouse', 50, 19.99, 'IO_DEVICE', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Printer1', 'Really interesting printer, prints everything', 100, 150, 'IO_DEVICE', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop dell', 'Really cute laptop', 100, 3500, 'LAPTOP', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop apple', 'Really cute laptop', 100, 3699, 'LAPTOP', 14);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop hp', 'Really cute laptop', 120, 2500, 'LAPTOP', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Laptop lenovo', 'Really cute laptop', 120, 299, 'LAPTOP', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Latpop asus', 'Really cute laptop', 120, 2000, 'LAPTOP', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Headphones1', 'Really cute headphones', 50, 50, 'IO_DEVICE', 15);
INSERT INTO equipments (name, description, quantity, price, equipment_type, user_id) VALUES ('Headdphones2', 'Really cute headphones', 50, 65, 'IO_DEVICE', 15);

INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id) VALUES ('CRS', 'Java Advanced Masterclass', 'Really good lessons', 'Novi Sad', 250, 1641142800000, 1641402000000, 'Milan Marinkovic', 14);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id) VALUES ('CRS', 'Rust Advanced Masterclass', 'Really good lessons', 'Belgrade', 450, 1641560400000, 1641646800000, 'Jelena Cupac', 14);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, teacher, user_id) VALUES ('CRS', 'Golang Advanced Masterclass', 'Really good lessons', 'Vienna', 550, 1643731200000, 1643832000000, 'Aleksa Goljovic', 15);

INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id) VALUES ('CNF', 'International Conference on Information Systems', 'Really good conference', 'Novi Sad', 70, 1641646800000, 1641819600000, 14);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id) VALUES ('CNF', 'International Conference on Machine Learning', 'Really good conference', 'Belgrade', 200, 1641805200000, 1641830400000, 15);
INSERT INTO services ("type", name, description, location, price, start_date, end_date, user_id) VALUES ('CNF', 'International Conference on Software Engineering', 'Really good conference', 'London', 900, 1644051600000, 1644483600000, 15);
