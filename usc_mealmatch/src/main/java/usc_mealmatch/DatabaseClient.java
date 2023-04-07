package usc_mealmatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseClient {
	@FunctionalInterface
	protected interface DatabaseFunction<T> {
		T use(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet);
	}

	private static String getDBUrl() {
		return Util.getEnv("DB_URL");
	}

	private static String getDBUsername() {
		return Util.getEnv("DB_USER");
	}

	private static String getDBPassword() {
		return Util.getEnv("DB_PASSWORD");
	}

	/**
	 * Creates a connection to the MySQL database with the database name MealMatch
	 * The database's URL, username, and password are obtained from environment
	 * variables
	 * 
	 * @return Connection to the MySQL database
	 * @throws SQLException
	 */
	public static Connection createConnection() throws SQLException {
		String url = getDBUrl();
		String user = getDBUsername();
		String password = getDBPassword();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		return DriverManager
				.getConnection(String.format("jdbc:mysql://%s/usc_mealmatch?user=%s&password=%s", url, user, password));
	}

	/**
	 * Wrapper to automatically initialize and cleanup database connection
	 * PreparedStatement and ResultSet is null, so they have to be assigned a value
	 * You do not have to close Connector, PreparedStatement, and ResultSet
	 * 
	 * @param <T>      return type of the lambda function
	 * @param function lambda function that uses the database connection
	 * @return results from lambda function
	 * @throws SQLException
	 */
	public static <T> T useDatabase(DatabaseFunction<T> function) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseClient.createConnection();

			return function.use(connection, preparedStatement, resultSet);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
