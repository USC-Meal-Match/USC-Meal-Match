/*
Coded by Genia Druzhinina, Ken Xu, Joey Yap
04/03/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UserProfile {
	private static final String DEFAULT_PIC_URL = "https://d2w9rnfcy7mm78.cloudfront.net/14089132/original_4e0a6e7e65dcef6e22f63967f42291ac.png";

	private Integer userID; // ID of the user
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

	public UserProfile(int userID, String picURL, List<String> pref, List<String> dietRstr) { // initialize the user //
																								// // profile
		this.userID = userID;
		this.picURL = picURL;
		this.pref = pref;
		this.dietRstr = dietRstr;
	}

	public boolean insertDatabase() {
		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					if (picURL == null) {
						picURL = DEFAULT_PIC_URL;
					}

					preparedStatement = connection.prepareStatement(
							"INSERT INTO user_profiles (user_id, preference_list, dining_hall_id, profile_pic_url, user_diet_restrictions) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE user_id = user_id");

					preparedStatement.setInt(1, userID);
					preparedStatement.setString(2, String.join(",", pref));
					preparedStatement.setInt(3, 1);
					preparedStatement.setString(4, picURL);
					preparedStatement.setString(5, String.join(",", dietRstr));

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

	public Integer getUserID() { // return the user ID
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
