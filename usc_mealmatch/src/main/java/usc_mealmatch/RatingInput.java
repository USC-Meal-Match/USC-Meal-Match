package usc_mealmatch;


//this class is for storing the (rating, dininghall id, userid)
public class RatingInput 
{
	private int rating;
	private int dininghaID;
	private int userID;
	
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getDininghaID() {
		return dininghaID;
	}
	public void setDininghaID(int dininghaID) {
		this.dininghaID = dininghaID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
