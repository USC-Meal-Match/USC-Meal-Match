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

@WebServlet("/profile")
public class UserProfilePostAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader in = req.getReader();
		PrintWriter pw = resp.getWriter();

		try {
			resp.setContentType("application/json");
			resp.setHeader("Access-Control-Allow-Origin", "*");

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			UserProfile curr = gson.fromJson(in, UserProfile.class);

			if (curr == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"No request body provided\"}");
				return;
			}

			if (curr.getUserID() == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"User ID not provided\"}");
				return;
			}

			if (curr.getPref() == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"User preferences not provided\"}");
				return;
			}

			if (curr.getDietRstr() == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Diet restrictions not provided\"}");
				return;
			}

			if (curr.insertDatabase()) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
				pw.println("{\"error\": \"User ID is invalid\"}");
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