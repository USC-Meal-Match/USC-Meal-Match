/*
Coded by Genia Druzhinina, Ken Xu, Joey Yap, Kyle Gan
04/03/2023 :: UPDATED 04/10/2023
*/
package usc_mealmatch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiningHallMatcher {
	public static int match(int userID) {
		UserProfile userProfile = UserProfile.getUserProfile(userID);
		List<DiningHall> diningHalls = Menus.getMenus().getDiningHalls();

		if (userProfile != null) {
			return computeDiningHall(userProfile, diningHalls);
		} else {
			return -1;
		}
	}

	// match the user to their best dining hall
	public static int computeDiningHall(UserProfile user, List<DiningHall> diningHalls) {
		List<String> preferences = user.getPref();
		List<String> restrictions = user.getDietRstr();

		boolean vegan = false;
		boolean vegetarian = false;

		Set<String> userR_set = new HashSet<String>();
		for (String restr : restrictions) {
			if (restr.toLowerCase().equals("vegan")) {
				vegan = true;
			} else if (restr.toLowerCase().equals("vegetarian")) {
				vegetarian = true;
			} else {
				userR_set.add(restr.toLowerCase());
			}
		}

		int bestDiningHall = -1;
		double bestMatches = -1;
		double bestRating = -1;

		for (int i = 0; i < diningHalls.size(); i++) {
			DiningHall diningHall = diningHalls.get(i);

			double total = 0;
			int preferenceMatches = 0;
			int vegMatches = 0;

			for (MenuItem menuItem : diningHall.getMenu()) {
				List<String> menuItemRestr = menuItem.getDietRstr();

				boolean itemVegan = false;
				boolean itemVegetarian = false;

				boolean hasR = false;
				for (String r : menuItemRestr) {
					if (r.toLowerCase().equals("vegan")) {
						itemVegan = true;
					} else if (r.toLowerCase().equals("vegetarian")) {
						itemVegetarian = true;
					} else if (userR_set.contains(r.toLowerCase())) {
						hasR = true;
					}
				}
				if (hasR)
					continue;
				if (vegetarian) {
					if (!itemVegetarian) {
						continue;
					}
				}
				if (vegan) {
					if (!itemVegan) {
						continue;
					}
				}
				// At this point the item is guaranteed to be "edible" now we see if we should
				// add bonus points
				if ((vegetarian && itemVegetarian) || (vegan && itemVegan)) {
					vegMatches++;
				}
				for (String preference : preferences) {
					// if the item name contains a preference it is a potential match
					if (menuItem.getName().toLowerCase().contains(preference.toLowerCase())) {
						preferenceMatches++;
					}
					break;
				}
			}

			total = preferenceMatches + 0.5 * vegMatches;
			if (total > bestMatches) {
				bestMatches = total;
				bestDiningHall = i;
				bestRating = Rating.getRating(diningHall.getDiningHallID());
			} else if (total == bestMatches) {
				if (Rating.getRating(diningHall.getDiningHallID()) > bestRating) {
					bestDiningHall = i;
					bestRating = Rating.getRating(diningHall.getDiningHallID());
				}
			}
		}
		return bestDiningHall;
	}
}
