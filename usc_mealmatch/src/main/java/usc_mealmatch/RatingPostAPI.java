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

@WebServlet("/rating")
public class RatingPostAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		BufferedReader in = req.getReader();

		try {
			resp.setContentType("application/json");
			resp.setHeader("Access-Control-Allow-Origin", "*");

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			RatingInput curr = gson.fromJson(in, RatingInput.class);

			if (curr == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"No request body provided\"}");
				return;
			}

			Integer rating = curr.getRating();
			if (rating == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Rating not provided\"}");
				return;
			} else if (rating > 5 || rating < 1) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Rating should be between 1 and 5\"}");
			}

			Integer diningHallID = curr.getDininghaID();
			if (diningHallID == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Dining hall ID not provided\"}");
				return;
			}

			Integer userID = curr.getUserID();
			if (userID == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"User ID not provided\"}");
				return;
			}

			if (Rating.setRating(rating, diningHallID, userID)) {
				// setting status
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
				pw.println("{\"error\": \"Dining hall ID or user ID is invalid\"}");
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
