/*
Coded by Joey Yap
04/09/2023
*/
package usc_mealmatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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
		BufferedReader in = req.getReader();
		PrintWriter pw = resp.getWriter();

		try {
			resp.setContentType("application/json");
			resp.setHeader("Access-Control-Allow-Origin", "*");

			// getting user input from the browser
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			ProfilePicPostInput curr = gson.fromJson(in, ProfilePicPostInput.class);

			if (curr == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"No request body provided\"}");
				return;
			}

			Integer userID = curr.getUserID();
			if (userID == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"User ID not provided\"}");
				return;
			}

			String url = curr.getProfilePicURL();
			if (url == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Profile picture URL not provided\"}");
				return;
			}

			UserProfile profile = UserProfile.getUserProfile(userID);
			if (profile == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"User ID is invalid\"}");
				return;
			}

			if (profile.setPicURL(url)) {
				// setting status
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
				pw.println("{\"error\": \"Failed to update profile picture\"}");
				return;
			}
		} catch (JsonSyntaxException e) {
			resp.setStatus(400);
			pw.println("{\"error\": \"Invalid JSON\"}");
		} finally {
			pw.flush();
			pw.close();
			in.close();
		}
	}
}
