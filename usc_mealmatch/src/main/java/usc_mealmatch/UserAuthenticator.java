package usc_mealmatch;

import java.sql.SQLException;

public class UserAuthenticator {
	/**
	 * Logs the user into the system using their email and password
	 * 
	 * @param email
	 * @param password
	 * @return true if user successfully logged in, or false if login failed
	 */
	public static boolean login(String email, String password) { // authenticate the user's login input
		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("SELECT * FROM auth WHERE user_email=? AND user_password=?");

					preparedStatement.setString(1, email);
					preparedStatement.setString(2, password);

					resultSet = preparedStatement.executeQuery();

					if (resultSet.next()) {
						// At least one row matches the given email and password
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

	/**
	 * Creates a new user with the given email and password
	 * 
	 * @param email
	 * @param password
	 * @return true if user successfully signed up, or false if signup failed (e.g.
	 *         account already exists)
	 */
	public static boolean signup(String email, String password) {
		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("INSERT INTO auth (user_email, user_password) VALUE (?, ?)");

					preparedStatement.setString(1, email);
					preparedStatement.setString(2, password);

					resultSet = preparedStatement.executeQuery();

					return true;
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