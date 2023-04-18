/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/rating/*")
public class RatingGetAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();

		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		try {
			int id = Integer.parseInt(req.getPathInfo().substring(1));
			double rating = Rating.getRating(id);

			if (rating > 0) {
				resp.setStatus(200);
				pw.print(String.format("{\"rating\": %f}", rating));
				pw.flush();
			} else {
				resp.setStatus(400);
				pw.print("{\"error\": \"Dining hall does not exist or has no ratings yet\"}");
			}
		} catch (NumberFormatException e) {
			resp.setStatus(400);
			pw.print("{\"error\": \"Invalid dining hall ID provided\"}");
		} finally {
			pw.flush();
			pw.close();
		}
	}
}
