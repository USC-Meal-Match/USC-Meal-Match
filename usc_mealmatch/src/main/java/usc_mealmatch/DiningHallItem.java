package usc_mealmatch;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiningHallItem {
	private String name;

	@SerializedName("items")
	private ArrayList<String> items;
	
	private double avgRating;
	
	//@SerializedName("dietRstr")
	//private ArrayList<String> dietRstr;
	
	public DiningHallItem(String name, int dininghallID, List<MenuItem> menuItems)
	{
		this.name = name;
		for(int i=0;i<menuItems.size();i++)
		{
			items.add(menuItems.get(i).getName());
		}
		avgRating = Rating.getRating(dininghallID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return items.size();
	}

	public void addItem(String diet) {
		items.add(diet);
	}

	public String getItem(int index) {
		return items.get(index);
	}

	public double getAvgRating() {
		return avgRating;
	}

}
