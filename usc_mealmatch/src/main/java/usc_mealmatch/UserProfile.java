/*
Coded by Genia Druzhinina
04/03/2023
*/

package usc_mealmatch;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UserProfile {
	private int userID; // ID of the user
	private String picURL; // URL of user profile pic
	private List<String> pref; // user's preference list
	private List<String> dietRstr; // user's dietary restriction list

	public static UserProfile getUserProfile(int userID) {
		try {
			UserProfile profile = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection.prepareStatement("SELECT * FROM user_profiles WHERE user_id=?");

					preparedStatement.setInt(1, userID);

					resultSet = preparedStatement.executeQuery();

					if (resultSet.next()) {
						// Profile exists
						String picUrl = resultSet.getString("profile_pic_url");
						List<String> pref = Arrays.asList(resultSet.getString("preference_list").split(","));
						List<String> dietRstr = Arrays.asList(resultSet.getString("user_diet_restrictions").split(","));
						return new UserProfile(userID, picUrl, pref, dietRstr);
					} else {
						return null;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return null;
				}
			});

			return profile;
		} catch (SQLException e) {
			return null;
		}
	}

	public UserProfile(int userID, String picURL, List<String> pref, List<String> dietRstr) { // initialize the user
																								// profile
		this.userID = userID;
		this.picURL = picURL;
		this.pref = pref;
		this.dietRstr = dietRstr;
	}

	public int getUserID() { // return the user ID
		return userID;
	}

	public String getPicURL() { // return the URL
		return picURL;
	}

	public boolean setPicURL(String picURL) { // set the URL
		this.picURL = picURL;

		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("UPDATE user_profiles SET profile_pic_url = ? WHERE user_id = ?");

					preparedStatement.setString(1, picURL);
					preparedStatement.setInt(2, userID);

					int count = preparedStatement.executeUpdate();

					if (count > 0) {
						return true;
					} else {
						return false;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return false;
				}
			});

			return success;
		} catch (SQLException e) {
			return false;
		}
	}

	public List<String> getPref() { // return the preferences
		return pref;
	}

	public boolean setPref(List<String> pref) { // set the preferences
		this.pref = pref;

		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("UPDATE user_profiles SET preference_list = ? WHERE user_id = ?");

					preparedStatement.setString(1, String.join(",", pref));
					preparedStatement.setInt(2, userID);

					int count = preparedStatement.executeUpdate();

					if (count > 0) {
						return true;
					} else {
						return false;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return false;
				}
			});

			return success;
		} catch (SQLException e) {
			return false;
		}
	}

	public List<String> getDietRstr() { // return the restrictions
		return dietRstr;
	}

	public boolean setDietRstr(List<String> dietRstr) { // set the restrictions
		this.dietRstr = dietRstr;

		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("UPDATE user_profiles SET user_diet_restrictions = ? WHERE user_id = ?");

					preparedStatement.setString(1, String.join(",", dietRstr));
					preparedStatement.setInt(2, userID);

					int count = preparedStatement.executeUpdate();

					if (count > 0) {
						return true;
					} else {
						return false;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return false;
				}
			});

			return success;
		} catch (SQLException e) {
			return false;
		}
	}
}