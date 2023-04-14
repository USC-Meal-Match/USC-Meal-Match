package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/match/*")
public class MatchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		try {
			int userID = Integer.parseInt(req.getPathInfo().substring(1));
			int match = DiningHallMatcher.match(userID);

			if (match >= 0) {
				resp.setStatus(200);
				PrintWriter pw = resp.getWriter();
				pw.printf("{\"match\": %d}", match);
				pw.flush();
				pw.close();
			} else {
				resp.setStatus(400);
			}
		} catch (NumberFormatException e) {
			resp.setStatus(400);
		}
	}
}