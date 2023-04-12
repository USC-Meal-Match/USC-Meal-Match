package usc_mealmatch;

import java.sql.SQLException;
import java.sql.Statement;

public class UserAuthenticator {
	/**
	 * Logs the user into the system using their email and password
	 * 
	 * @param email
	 * @param password
	 * @return user ID if user successfully logged in, or -1 if login failed
	 */
	public static int login(String email, String password) { // authenticate the user's login input
		try {
			int userID = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("SELECT * FROM auth WHERE user_email=? AND user_password=?");

					preparedStatement.setString(1, email);
					preparedStatement.setString(2, password);

					resultSet = preparedStatement.executeQuery();

					if (resultSet.next()) {
						// At least one row matches the given email and password
						return resultSet.getInt("user_id");
					} else {
						return -1;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return -1;
				}
			});

			return userID;
		} catch (SQLException e) {
			return -1;
		}
	}

	/**
	 * Creates a new user with the given email and password
	 * 
	 * @param email
	 * @param password
	 * @return user ID if user successfully signed up, -1 false if signup failed
	 *         (e.g. account already exists)
	 */
	public static int signup(String email, String password) {
		try {
			int userID = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection.prepareStatement(
							"INSERT INTO auth (user_email, user_password) VALUE (?, ?)",
							Statement.RETURN_GENERATED_KEYS);

					preparedStatement.setString(1, email);
					preparedStatement.setString(2, password);

					preparedStatement.executeUpdate();

					resultSet = preparedStatement.getGeneratedKeys();

					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						return -1;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return -1;
				}
			});

			return userID;
		} catch (SQLException e) {
			return -1;
		}
	}
}