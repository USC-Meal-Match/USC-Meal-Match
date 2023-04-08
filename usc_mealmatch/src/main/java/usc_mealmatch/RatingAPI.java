package usc_mealmatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/rating")
public class RatingAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		BufferedReader in = req.getReader();
		String json = in.readLine();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Rating curr = gson.fromJson(json, Rating.class);

		// todo: handle the ratings we get from the website
		// testing viablility of this API
		if (Rating.setRating(0, 0, 0)) {
			// setting status
			resp.setStatus(200);
			System.out.println("test case successful");
			resp.setContentType("application/json");
			PrintWriter pw = resp.getWriter();
			pw.print("{\"Xu\": \"Sup\"}");
			pw.flush();
		} else {
			resp.setStatus(400);
			System.out.println("Test case failed");
		}
	}
}
