/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/diet-restrictions")
public class DietPreferenceListAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader in = req.getReader();
		PrintWriter pw = resp.getWriter();

		try {
			resp.setContentType("application/json");
			resp.setHeader("Access-Control-Allow-Origin", "*");

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			DietPreferenceList curr = gson.fromJson(req.getReader(), DietPreferenceList.class);

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

			List<String> dietRstr = curr.getDietList();
			if (dietRstr == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Diet restrictions not provided\"}");
				return;
			}

			UserProfile profile = UserProfile.getUserProfile(userID);

			if (profile == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"User ID is invalid\"}");
				return;
			}

			// setting the user's dish preference list to the list curr acquired
			if (profile.setDietRstr(dietRstr)) {
				// setting status
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
				pw.println("{\"error\": \"Failed to update diet restrictions\"}");
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
