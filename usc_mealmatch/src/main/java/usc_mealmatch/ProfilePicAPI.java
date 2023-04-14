package usc_mealmatch;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile-pic")
public class ProfilePicAPI extends HttpServlet {
	private class ProfilePicPostInput {
		private Integer userID;
		private String profilePicURL;

		public Integer getUserID() {
			return userID;
		}

		public String getProfilePicURL() {
			return profilePicURL;
		}
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		// getting user input from the browser
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		ProfilePicPostInput curr = gson.fromJson(req.getReader(), ProfilePicPostInput.class);
		UserProfile profile = UserProfile.getUserProfile(curr.getUserID());

		if (profile != null && profile.setPicURL(curr.getProfilePicURL())) {
			// setting status
			resp.setStatus(200);
		} else {
			resp.setStatus(400);
		}
	}
}
