/*
Coded by Ken Xu, Joey Yap
04/07/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

//this class is for storing the (rating, dininghall id, userid)
public class RatingInput {
	private Integer rating;
	private Integer diningHallID;
	private Integer userID;

	public Integer getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Integer getDininghaID() {
		return diningHallID;
	}

	public void setDininghaID(int dininghaID) {
		this.diningHallID = dininghaID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
