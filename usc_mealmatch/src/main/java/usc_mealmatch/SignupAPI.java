/*
Coded by Joey Yap, Ken Xu
04/09/2023 :: UPDATED 04/12/2023
*/
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

@WebServlet("/signup")
public class SignupAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		BufferedReader in = req.getReader();

		try {
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setContentType("application/json");

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			EmailPassword curr = gson.fromJson(in, EmailPassword.class);

			if (curr == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"No request body provided\"}");
				return;
			}

			String email = curr.getEmail();
			if (email == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Email not provided\"}");
				return;
			}

			String password = curr.getPassword();
			if (password == null) {
				resp.setStatus(400);
				pw.println("{\"error\": \"Password not provided\"}");
				return;
			}

			int userID = UserAuthenticator.signup(email, password);

			if (userID >= 0) {
				// setting status
				resp.setStatus(200);
				pw.print("{\"signup\": true, \"userID\": \"" + userID + "\"}");
			} else {
				resp.setStatus(409);
				pw.print("{\"signup\": false, \"error\": \"Email already exists\"}");
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
