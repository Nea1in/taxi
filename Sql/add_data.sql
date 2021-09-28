use service_taxi;

LOCK TABLES `roles` WRITE;
INSERT INTO `roles` VALUES
(1, 'Admin'),
(2, 'User');
UNLOCK TABLES;

LOCK TABLES `categories` WRITE;
INSERT INTO `categories` VALUES
(1, 'Sedan',3),
(2, 'Wagon',2),
(3, 'Hatchback',4),
(4, 'Minivan',6),
(5, 'SUV (crossover)',5),
(6, 'Compartment',2),
(7, 'Convertible',3),
(8, 'Pickup',1),
(9, 'Minibus',7);
UNLOCK TABLES;


LOCK TABLES `users` WRITE;
INSERT INTO `users`(login, password, email,role_id) VALUES
('admin',MD5('12345'),'admin@gmail.com',1);
UNLOCK TABLES;

LOCK TABLES `cars` WRITE;
INSERT INTO `cars` VALUES
(1 ,1 ,'ready_to_order'),
(2 ,1 ,'not_active'),
(3 ,2,'ready_to_order'),
(4 ,2,'not_active'),
(5 ,3 ,'ready_to_order'),
(6 ,3 ,'not_active'),
(7 ,4 ,'ready_to_order'),
(8 ,4 ,'not_active'),
(9 ,5 ,'ready_to_order'),
(10 ,5 ,'not_active'),
(11 ,6 ,'ready_to_order'),
(12 ,6 ,'not_active'),
(13 ,7 ,'ready_to_order'),
(14 ,7,'not_active'),
(15 ,8,'ready_to_order'),
(16 ,8 ,'not_active'),
(17 ,9 ,'ready_to_order'),
(18 ,9 ,'not_active');
UNLOCK TABLES;
