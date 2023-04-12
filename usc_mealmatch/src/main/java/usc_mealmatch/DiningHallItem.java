package usc_mealmatch;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

public class DiningHallItem {
	private String name;

	@SerializedName("items")
	private List<String> items;

	private double avgRating;

	public DiningHallItem(String name, int dininghallID, List<MenuItem> menuItems) {
		this.name = name;
		items = menuItems.stream().map(item -> item.getName()).collect(Collectors.toList());
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
