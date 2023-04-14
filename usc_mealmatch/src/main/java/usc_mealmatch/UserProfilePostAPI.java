package usc_mealmatch;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class UserProfilePostAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

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