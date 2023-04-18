/*
Coded by Joey Yap
04/09/2023
*/
package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile/*")
public class UserProfileGetAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();

		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			int id = Integer.parseInt(req.getPathInfo().substring(1));
			UserProfile profile = UserProfile.getUserProfile(id);

			if (profile != null) {
				resp.setStatus(200);
				String json = gson.toJson(profile);
				pw.println(json);
			} else {
				resp.setStatus(400);
				pw.print("{\"error\": \"User does not exist or could not find a suitable match\"}");
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
