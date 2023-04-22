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
		PrintWriter pw = resp.getWriter();

		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		try {
			int userID = Integer.parseInt(req.getPathInfo().substring(1));
			int match = DiningHallMatcher.match(userID);

			if (match >= 0) {
				resp.setStatus(200);
				pw.printf("{\"match\": %d}", match);
			} else {
				resp.setStatus(400);
				pw.print("{\"error\": \"User does not exist or has no profile yet\"}");
			}
		} catch (NumberFormatException e) {
			resp.setStatus(400);
			pw.print("{\"error\": \"Invalid user ID provided\"}");
		} finally {
			pw.flush();
			pw.close();
		}
	}
}