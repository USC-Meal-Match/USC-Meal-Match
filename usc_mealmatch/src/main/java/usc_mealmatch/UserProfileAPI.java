package usc_mealmatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class UserProfileAPI extends HttpServlet {
	private class UserProfileGetInput {
		private Integer userID;

		public Integer getUserID() {
			return userID;
		}
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin: ", "*");

		BufferedReader in = req.getReader();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		UserProfileGetInput curr = gson.fromJson(in, UserProfileGetInput.class);

		if (curr.getUserID() != null) {
			int id = curr.getUserID();
			UserProfile profile = UserProfile.getUserProfile(id);
			if (profile != null) {
				resp.setStatus(200);
				PrintWriter pw = resp.getWriter();
				String json = gson.toJson(profile);
				pw.print(json);
				pw.flush();
				pw.close();
			} else {
				resp.setStatus(400);
			}
		} else {
			resp.setStatus(400);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin: ", "*");

		BufferedReader in = req.getReader();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		UserProfile curr = gson.fromJson(in, UserProfile.class);

		if (curr != null && curr.insertDatabase()) {
			resp.setStatus(200);
		} else {
			resp.setStatus(400);
		}
	}
}
