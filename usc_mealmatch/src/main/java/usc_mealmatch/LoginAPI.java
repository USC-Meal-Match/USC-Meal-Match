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

//@WebServlet(name = "Servlet", urlPatterns = {"/login"})

@WebServlet("/login")
public class LoginAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader in = req.getReader();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		EmailPassword curr = gson.fromJson(in, EmailPassword.class);

		String email = curr.getEmail();
		String password = curr.getPassword();

		PrintWriter pw = resp.getWriter();

		if (UserAuthenticator.login(email, password)) {
			// setting status
			resp.setStatus(200);
			resp.setHeader("Access-Control-Allow-Origin: ", "*");
			System.out.println("test case successful");
			resp.setContentType("application/json");
			pw.print("{\"authenticated\": \"true\"}");
			pw.flush();
		} else {
			resp.setStatus(400);
			System.out.println("Test case failed");
			pw.print("{\"authenticated\": \"false\"}");
			pw.flush();
		}

		pw.close();
		in.close();
	}
}
