/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

import java.sql.SQLException;

public class Rating {
	/**
	 * @param diningHallID ID of the dining hall for which we are querying the
	 *                     rating
	 * @return the rating for the specified dining hall, or -1 if it does not exist
	 */
	public static double getRating(int diningHallID) {
		try {
			double rating = (double) DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection
							.prepareStatement("SELECT AVG(rating_given) 'rating' FROM ratings WHERE dining_hall_id=?");

					preparedStatement.setInt(1, diningHallID);

					resultSet = preparedStatement.executeQuery();

					if (resultSet.next()) {
						// Rating exists
						return resultSet.getDouble("rating");
					} else {
						return -1;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return -1;
				}
			});

			return rating;
		} catch (SQLException e) {
			return -1;
		}
	}

	/**
	 * @param rating
	 * @param diningHallId
	 * @param userId
	 * @return true if rating is successfully added to the database
	 */
	public static boolean setRating(int rating, int diningHallID, int userID) {
		try {
			boolean success = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection.prepareStatement(
							"INSERT INTO ratings (dining_hall_id, user_id, rating_given) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE rating_given = rating_given");

					preparedStatement.setInt(1, diningHallID);
					preparedStatement.setInt(2, userID);
					preparedStatement.setInt(3, rating);

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
