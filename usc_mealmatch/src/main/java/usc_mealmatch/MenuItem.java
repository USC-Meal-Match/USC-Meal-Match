package usc_mealmatch;

import java.util.List;

public class MenuItem {
	private String name;
	private List<String> dietRstr;

	public MenuItem(String name, List<String> dietRtsr) {
		this.name = name;
		this.dietRstr = dietRtsr;
	}

	public String getName() {
		return name;
	}

	public List<String> getDietRstr() {
		return dietRstr;
	}
}
