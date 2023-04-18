/*
Coded by Ken Xu
04/06/2023 :: UPDATED 04/07/2023
*/
package usc_mealmatch;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DishPreferenceList {
	@SerializedName("DishPreference")
	private ArrayList<String> DishPreference;
	private Integer userID;// primary key of a user

	public int getSize() {
		return DishPreference.size();
	}

	public void addDish(String diet) {
		DishPreference.add(diet);
	}

	public String getDish(int index) {
		return DishPreference.get(index);
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public ArrayList<String> getDishList() {
		return DishPreference;
	}
}
