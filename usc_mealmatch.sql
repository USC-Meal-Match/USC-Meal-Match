/*
Coded by Genia Druzhinina
04/03/2023
*/

DROP DATABASE IF EXISTS usc_mealmatch;
CREATE DATABASE usc_mealmatch;
USE usc_mealmatch;

CREATE TABLE auth (
	user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_email VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL /*password wasn't an available variable*/
);
INSERT INTO auth (user_id, user_email, user_password)
	VALUES (1234567890, 'useremail@gmail.com', 'password');
    
CREATE TABLE dining_halls (
	dining_hall_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    dining_hall_name VARCHAR(255) 
);
INSERT INTO dining_halls (dining_hall_id, dining_hall_name)
	VALUES (0987654321, 'EVK');
    
CREATE TABLE user_profiles ( /*profile wasn't an available name*/
	user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    preference_list VARCHAR(255) NOT NULL,
    dining_hall_id INT NOT NULL,
    profile_pic_url VARCHAR(255) NOT NULL,
    user_diet_restrictions VARCHAR(255) NOT NULL,
    FOREIGN KEY fk1(user_id) REFERENCES auth(user_id),
	FOREIGN KEY fk2(dining_hall_id) REFERENCES dining_halls(dining_hall_id)
);
INSERT INTO user_profiles (user_id, preference_list, dining_hall_id, profile_pic_url, user_diet_restrictions)
	VALUES (1234567890, 'chicken,ramen,pizza', 0987654321, 'google.com', 'v,k,gf,vg,p');

CREATE TABLE menu (
	item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    dining_hall_id INT NOT NULL,
    diet_restrictions VARCHAR(255) NOT NULL,
    FOREIGN KEY fk1(dining_hall_id) REFERENCES dining_halls(dining_hall_id)
);
INSERT INTO menu (item_id, item_name, dining_hall_id, diet_restrictions)
	VALUES (1122334455, 'pizza', 0987654321, 'nut,dairy,gluten');

CREATE TABLE ratings (
	rating_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	dining_hall_id INT NOT NULL,
    user_id INT NOT NULL,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, /*MM/DD/YYYY, date was an unavailable variable*/
    rating_given INT NOT NULL,
    FOREIGN KEY fk1(dining_hall_id) REFERENCES dining_halls(dining_hall_id),
    FOREIGN KEY fk2(user_id) REFERENCES auth(user_id)
);
INSERT INTO ratings (rating_id, dining_hall_id, user_id, date_added, rating_given)
	VALUES (0099887766, 0987654321, 1234567890, CURRENT_TIME(), 5);

SELECT * FROM auth;
SELECT * FROM dining_halls;
SELECT * FROM user_profiles;
SELECT * FROM menu;
SELECT * FROM ratings;