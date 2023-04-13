/*
Coded by Ken Xu, Joey Yap
04/07/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

//this class is for storing the (rating, dininghall id, userid)
public class RatingInput {
	private int rating;
	private int dininghallID;
	private int userID;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getDininghaID() {
		return dininghallID;
	}

	public void setDininghaID(int dininghaID) {
		this.dininghallID = dininghaID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
