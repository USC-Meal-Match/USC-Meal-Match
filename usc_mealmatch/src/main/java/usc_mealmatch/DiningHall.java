/*
Coded by Genia Druzhinina
04/03/2023
*/

package usc_mealmatch;

import java.util.List;

public class DiningHall {
	private int diningHallID; // ID of the dining hall
	private List<MenuItem> menu; // menu of the dining hall

	public DiningHall(int diningHallID, List<MenuItem> menu) { // initialize the dining hall
		this.diningHallID = diningHallID;
		this.menu = menu;
	}

	public int getDiningHallID() { // return the dining hall ID
		return diningHallID;
	}

	public List<MenuItem> getMenu() { // return the menu
		return menu;
	}

	public void addToMenu(MenuItem item) {
		menu.add(item);
	}
}