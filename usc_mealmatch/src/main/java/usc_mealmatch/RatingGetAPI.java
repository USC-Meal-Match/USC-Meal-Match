/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/09/2023
*/
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

@WebServlet("/rating/*")
public class RatingGetAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		BufferedReader in = req.getReader();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		RatingInput curr = gson.fromJson(in, RatingInput.class);

		if (Rating.setRating(curr.getRating(), curr.getDininghaID(), curr.getUserID())) {
			// setting status
			resp.setStatus(200);
			resp.setHeader("Access-Control-Allow-Origin", "*");
		} else {
			resp.setStatus(400);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		try {
			int id = Integer.parseInt(req.getPathInfo().substring(1));
			double rating = Rating.getRating(id);

			if (rating > 0) {
				resp.setStatus(200);
				PrintWriter pWriter = resp.getWriter();
				pWriter.print(String.format("{\"rating\": %f}", rating));
				pWriter.flush();
				pWriter.close();
			} else {
				resp.setStatus(400);
			}
		} catch (NumberFormatException e) {
			resp.setStatus(400);
		}
	}
}
