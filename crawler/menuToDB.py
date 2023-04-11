"""
Coded by Genia Druzhinina
04/07/2023 :: UPDATED 04/11/2023
"""
import mysql.connector
from main import *

try:
	connection = mysql.connector.connect(host='34.31.63.204', 
				      database='usc_mealmatch', 
					  user='root', 
					  password='csci201') #connects to the database
	
	cursor = connection.cursor() #connect the cursor

	cursor.execute("TRUNCATE TABLE menu") #delete the pre-existing menu
	cursor.execute("SELECT * from dining_halls") #select the dining_halls table
	
	diningHalls = cursor.fetchall() #retrieve the dining_halls table
	query = "INSERT INTO menu(item_name, dining_hall_id, diet_restrictions) VALUES (%s, %s, %s)" #create the query for prepared statement
	
	for row in diningHalls: #go through all the dining halls
		diningHallID = row[0] #gets the ID of the dining hall
		for i in range(2): #crawl through each dining hall's session
			menus = crawl_menus(i+1, diningHallID) #crawl the menu
			for j in range(len(menus)): #input the item name, dining hall ID, and diet restrictions
				item_name = str(menus[j]["name"])
				diet_restrictions = str(", ".join(menus[j]["allergy"]))
				
				cursor.execute(query, (item_name, str(diningHallID), diet_restrictions)) #execute prepared statement
	connection.commit()
	cursor.close()
	
finally: #close the connections
	if connection.is_connected():
		connection.close()