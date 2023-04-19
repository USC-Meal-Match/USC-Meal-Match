/*
Coded by Genia Druzhinina, Joey Yap
04/03/2023 :: UPDATED 04/12/2023
*/

DROP DATABASE IF EXISTS usc_mealmatch;
CREATE DATABASE usc_mealmatch;
USE usc_mealmatch;

CREATE TABLE auth (
	user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_email VARCHAR(255) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL /*password wasn't an available variable*/
);
    
CREATE TABLE dining_halls (
	dining_hall_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    dining_hall_name VARCHAR(255) 
);
INSERT INTO dining_halls (dining_hall_id, dining_hall_name)
	VALUES (1, 'parkside'),
		   (2, 'village'),
           (3, 'evk');
    
CREATE TABLE user_profiles ( /*profile wasn't an available name*/
	user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    preference_list VARCHAR(255) NOT NULL,
    dining_hall_id INT NOT NULL,
    profile_pic_url VARCHAR(255) NOT NULL,
    user_diet_restrictions VARCHAR(255) NOT NULL,
    FOREIGN KEY fk1(user_id) REFERENCES auth(user_id),
	FOREIGN KEY fk2(dining_hall_id) REFERENCES dining_halls(dining_hall_id)
);

CREATE TABLE menu (
	item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    dining_hall_id INT NOT NULL,
    diet_restrictions VARCHAR(255) NOT NULL,
    FOREIGN KEY fk1(dining_hall_id) REFERENCES dining_halls(dining_hall_id)
);

CREATE TABLE ratings (
	dining_hall_id INT NOT NULL,
    user_id INT NOT NULL,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, /*MM/DD/YYYY, date was an unavailable variable*/
    rating_given INT NOT NULL,
    PRIMARY KEY (user_id, dining_hall_id),
    FOREIGN KEY fk1(dining_hall_id) REFERENCES dining_halls(dining_hall_id),
    FOREIGN KEY fk2(user_id) REFERENCES auth(user_id)
);
