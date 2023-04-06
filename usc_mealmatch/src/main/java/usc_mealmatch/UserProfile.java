/*
Coded by Genia Druzhinina
04/03/2023
*/

package usc_mealmatch;

import java.util.List;

public class UserProfile {
	private int userID; //ID of the user
	private String picURL; //URL of user profile pic
	private List<String> pref; //user's preference list
	private List<String> dietRstr; //user's dietary restriction list
	
	public UserProfile(int userID, String picURL, List<String> pref, List<String> dietRstr) { //initialize the user profile
		this.userID = userID;
		this.picURL = picURL;
		this.pref = pref;
		this.dietRstr = dietRstr;
	}
	public int getUserID() { //return the user ID
		return userID;
	}
	public String getPicURL() { //return the URL
		return picURL;
	}
	public void setPicURL(String picURL) { //set the URL
		this.picURL = picURL;
	}
	public List<String> getPref() { //return the preferences
		return pref;
	}
	public void setPref(List<String> pref) { //set the preferences
		this.pref = pref;
	}
	public List<String> getDietRstr() { //return the restrictions
		return dietRstr;
	}
	public void setDietRstr(List<String> dietRstr) { //set the restrictions
		this.dietRstr = dietRstr;
	}
}