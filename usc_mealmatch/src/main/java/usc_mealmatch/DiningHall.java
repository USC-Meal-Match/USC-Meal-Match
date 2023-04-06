package usc_mealmatch;

import java.util.List;

public class DiningHall {
	private int diningHallID; //ID of the dining hall
	private List<String> menu; //menu of the dining hall
	
	public DiningHall(int diningHallID, List<String> menu) { //initialize the dining hall
		this.diningHallID = diningHallID;
		this.menu = menu;
	}
	public int getDiningHallID() { //return the dining hall ID
		return diningHallID;
	}
	public List<String> getMenu() { //return the menu
		return menu;
	}
}