package usc_mealmatch;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DiningHallItem {
	private String name;

	@SerializedName("items")
	private ArrayList<String> items;

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

}
