------------------------------
-- Populate t_auditorium table
------------------------------
INSERT INTO t_auditorium (name, seats, vip)
VALUES ('La Scala', 350, '12,13,14,15,16,17,18,19,20');
INSERT INTO t_auditorium (name, seats, vip)
VALUES ('Grand Opera', 450, '2,3,4,5,6,7');
INSERT INTO t_auditorium (name, seats, vip)
VALUES ('Carnegie Hall', 500, '12,13,14,15,16,17,18,19,20');
INSERT INTO t_auditorium (name, seats, vip)
VALUES ('Disco Radio Hall', 150, '1,7,13,27');

------------------------
-- Populate t_user table
------------------------
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Garry Potter', '2001-05-01', 'potter@gmail.com', '$2a$08$kpl4hDd9Q1f.OoKLqVcQXu7yZRiOMUZohjpea48ccInxRJF9cGnL.', 'BOOKING_MANAGER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Ron Weasley',  '2000-05-01', 'ron@gmail.com', '$2a$08$KUJMAPW2cAtwyYG6qc1pwuZdT1v2jYmruLzfE01T2FQoVnt6P171K', 'BOOKING_MANAGER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Germiona Grendjer', '2000-05-01', 'germiona@gmail.com', '$2a$08$WFoYKarHZjbQNGOcHjmsve96B/xb7T9gWs/ahrZsP8zQhmn3T/pYS', 'BOOKING_MANAGER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Rubeus Hagrid', '1972-05-01', 'hagrid@gmail.com', '$2a$08$ag1BJIzTI3V3u9i2PIYxDuDLQ5cSJUCzW3cl/1V7u4o.HuBJSq9ra', 'REGISTERED_USER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Severus Snape', '1980-05-01', 'snape@gmail.com', '$2a$08$GIJ6T6hfzfxiK9.oire8g.CtEhvqiZDqzNhsqBgrNpXmGjq2ZCdjW', 'REGISTERED_USER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Godric Gryffindor', '1980-05-01', 'griffindor@gmail.com', '$2a$08$q1/H5kZQBgJuEDd6sfBfn.8C47PFVJfvra.DjdplOpxnrCEBsj5k2', 'BOOKING_MANAGER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Salazar Slytherin', '1980-05-01', 'slytseryn@gmail.com', '$2a$08$w5GXzKCeq2dQGVoEHVq4KOFWNbm1RlU7Bl8hKrjp5yePv1FkuUuza', 'REGISTERED_USER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Helga Hufflepuff', '1980-05-01', 'hufflepuff@gmail.com', '$2a$08$af9CIpq6Uvpt2Joelz8wR.5TpXmwaw4mnNmFNlrBQj2bsS0wwdZIe', 'REGISTERED_USER', 1);
INSERT INTO t_user (name, birthday, email, password, role, enabled)
VALUES ('Rowena Ravenclaw', '1980-05-01', 'ravenclaw@gmail.com', '$2a$08$r3BOSoTeIjNsG83HfXPsxOhGfhNfjhwIwPPfA8oPcPUawNX5wmXaW', 'REGISTERED_USER', 1);

-------------------------------
-- Populate t_useraccount table
-------------------------------
INSERT INTO t_useraccount (balance, user_id) VALUES (1500.0, 6);
INSERT INTO t_useraccount (balance, user_id) VALUES (1500.0, 7);
INSERT INTO t_useraccount (balance, user_id) VALUES (1500.0, 8);
INSERT INTO t_useraccount (balance, user_id) VALUES (1500.0, 9);

------------------------
-- Populate t_event table
------------------------
INSERT INTO t_event (name, airDate, ticketPrice, rating, auditorium_id)
VALUES ('Green Mile', '2016-02-28', 60.0, 'HIGH', 1);
INSERT INTO t_event (name, airDate, ticketPrice, rating, auditorium_id)
VALUES ('Gone by the wind', '2016-02-28', 50.0, 'MEDIUM', 2);
INSERT INTO t_event (name, airDate, ticketPrice, rating, auditorium_id)
VALUES ('Mad Max: Furry road', '2016-02-28', 90.0, 'LOW', 3);
INSERT INTO t_event (name, airDate, ticketPrice, rating, auditorium_id)
VALUES ('Tow Waits tribute concert', '2016-02-28', 110.0, 'HIGH', 4);
--INSERT INTO t_event (name, airDate, ticketPrice, rating, auditorium_id)
--VALUES ('Fredie Mercurry tribute concert', '2016-02-28', 200.0, 'HIGH', 1);


--------------------------
-- Populate t_ticket table
--------------------------
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('23', 60.0, '2016-04-28', 1, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('32', 70.0, '2016-04-18', 2, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('17', 80.0, '2016-04-09', 3, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('47', 90.0, '2016-04-08', 4, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('72', 70.0, '2016-04-08', 1, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('49', 200.0, '2016-04-08', 3, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('57', 250.0, '2016-04-08', 4, 1);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('37', 250.0, '2016-04-08', 4, 6);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('54', 250.0, '2016-04-08', 4, 6);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('18', 250.0, '2016-04-08', 4, 6);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('33', 250.0, '2016-04-08', 4, 7);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('44', 250.0, '2016-04-08', 4, 7);
INSERT INTO t_ticket (name, price, onDate, event_id, user_id)
VALUES ('80', 250.0, '2016-04-08', 4, 7);

-------------------------------
-- Populate t_useraccount table
-------------------------------
INSERT INTO t_useraccount (balance, user_id)
VALUES (17600, 1);
INSERT INTO t_useraccount (balance, user_id)
VALUES (600, 2);
INSERT INTO t_useraccount (balance, user_id)
VALUES (800, 3);
INSERT INTO t_useraccount (balance, user_id)
VALUES (700, 4);







