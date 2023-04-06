package usc_mealmatch;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DishPreferenceList 
{
	@SerializedName("DishPreference")
	private ArrayList<String> DishPreference;
	private String userID;//primary key of a user
	
	public int getSize()
	{
		return DishPreference.size();
	}
	
	public void addDish(String diet)
	{
		DishPreference.add(diet);
	}
	
	public String getDish(int index)
	{
		return DishPreference.get(index);
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
