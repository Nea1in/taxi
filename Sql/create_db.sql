drop database if exists service_taxi;

create database service_taxi;

use service_taxi;

create table `roles` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(16) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `users` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `login`varchar(50) NOT NULL,
                         `password`varchar(50) NOT NULL,
                         `email` varchar(256) NOT NULL,
                         `role_id`int(11) NOT NULL default 2,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `email` (`email`),
                         FOREIGN KEY (role_id)  REFERENCES roles(Id) on delete cascade
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `categories` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `name` varchar(16) NOT NULL,
                              `capacity`int(10) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



create table `cars` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `category_id`int(11) NOT NULL,
                        `status`enum('ready_to_order', 'execute_order', 'not_active'),
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (category_id)  REFERENCES categories(id) on delete cascade

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `orders` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `user_id`int(11) NOT NULL,
                          `from` varchar(256) NOT NULL,
                          `to` varchar(256) NOT NULL,
                          `passenger` int(10) NOT NULL,
                          `category_id` int(11) NOT NULL,
                          `date` DATE ,
                          `price` DECIMAL NOT NULL,
                          `status` BOOLEAN DEFAULT FALSE,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (user_id)  REFERENCES users(id) on delete cascade,
                          FOREIGN KEY (category_id)  REFERENCES categories(id) on delete cascade
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
