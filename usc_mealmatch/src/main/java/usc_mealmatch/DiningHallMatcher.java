/*
Coded by Genia Druzhinina
04/03/2023
*/

package usc_mealmatch;

import java.util.List;

public class DiningHallMatcher {
	public int match(int userID) { //match the user to multiple dining halls
		return 0;
	}
	public int computeDiningHall(UserProfile user, List<DiningHall> diningHalls) { //match the user to their best dining hall
		List<String> preferences = user.getPref();
		// DIET RESTRICTIONS ARE IRRELEVANT
		// List<String> dietRstr = user.getDietRstr();

		Map<String, List<String>> diningHalls = new HashMap<String, List<String>>();

		for (String diningHall : diningHalls.keySet())
		{
			int matches = 0;
			for (String menu : diningHalls.get(diningHall))
			{
				for (String preference : preferences)
			}
		}
		
		
		return 0;
	}
}
