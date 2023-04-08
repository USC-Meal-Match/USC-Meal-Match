"""
Coded by Genia Druzhinina
04/07/2023
"""
import mysql.connector
from main import *

try:
	connection = mysql.connector.connect(host='localhost',
										database='usc_mealmatch',
										user='root',
										password='root') #not connecting
	
	menus = []
	
	cursor = connection.cursor()
	cursor.execute("TRUNCATE TABLE menu") #delete the pre-existing menu
	cursor.execute("SELECT * from dining_halls") #select the dining_halls table
	
	diningHalls = cursor.fetchall() #retrieve the dining_halls table
	
	for row in diningHalls: #go through all the dining halls
		diningHallID = row[0] #gets the ID of the dining hall
		diningHallName = row[1].lower() #gets the name of the dining hall
		if diningHallName == "parkside": #set the number for the dining hall crawler
			diningHallName = 1
		elif diningHallName == "village":
			diningHallName = 2
		elif diningHallName == "evk":
			diningHallName = 3
		for i in range(2): #crawl through each dining hall's session
			menus = crawl_menus(i+1, diningHallName)
			for j in range(len(menus)): #input the item name, dining hall ID, and diet restrictions
				cursor.execute("""INSERT INTO menu(item_name, dining_hall_id, diet_restrictions)
					VALUES (\"""" + menus[j]["name"] + """\", """ + str(diningHallID) + """, \"""" + "".join(menus[j]["allergy"]) + """\");""")	
	connection.commit()
	cursor.close()
	
finally:
	if connection.is_connected():
		connection.close()