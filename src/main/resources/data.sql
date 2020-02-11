INSERT INTO `auditoriums` (`name`, `amount_of_seats`) VALUES
('Green',	100),
('Yellow',	150);

INSERT INTO `events` (`id`, `name`, `base_price`, `rating`) VALUES
(1,	'FirstEvent',	100,	'LOW'),
(2,	'SecondEvent',	200,	'HIGH'),
(3,	'FirstEvent',	100,	'LOW'),
(4,	'SecondEvent',	200,	'HIGH');

INSERT INTO `event_has_auditoriums` (`auditoriums_name`, `air_date`, `events_id`) VALUES
('Green',	'2020-02-02 00:00:00',	1),
('Yellow',	'2020-01-01 00:00:00',	2);


INSERT INTO `roles` (`name`) VALUES
('ADMIN'),
('BOOKING_MANAGER'),
('RESGISTERED_USER');

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `birthday`, `password`) VALUES
(5,	'FirstUserFN',	'FirstUserLN',	'FUemail@gmail.com',	'2020-01-14',	'$2a$10$8qTiv8/sO1EpWo.9TwiVH.zR2ZRCVZl7Hl.0BTqORqrYqWH3MSv.K'),
(6,	'SecondUserFN',	'SecondUserLN',	'SUemail@gmail.com',	'2020-01-04',	'$2a$10$R044q12iSHa61OGeLHe54eoPMN4KH7AbfHw1nZJsZTWN/W0CV3s5m'),
(9,	'ThirdUserFN',	'SecondUserLN',	'TUemail@gmail.com',	'2020-02-01',	'$2a$10$3A1r/hthEjHYLwiV8n1wjeQMGF.2saBrx4bxeBAkS0WUD9V1p5/1C');

INSERT INTO `tickets` (`id`, `seat`, `date_time`, `orders_id`, `event_has_auditoriums_auditoriums_name`, `event_has_auditoriums_air_date`, `users_id`) VALUES
(1,	1,	'2020-01-27 08:23:41',	NULL,	'Green',	'2020-02-02 00:00:00',	5),
(2,	2,	'2020-01-27 08:23:41',	NULL,	'Green',	'2020-02-02 00:00:00',	5),
(3,	1,	'2019-01-27 08:23:41',	NULL,	'Yellow',	'2020-01-01 00:00:00',	6);

INSERT INTO `users_has_roles` (`users_id`, `roles_name`) VALUES
(5,	'RESGISTERED_USER'),
(6,	'ADMIN'),
(9,	'ADMIN'),
(9,	'BOOKING_MANAGER');
