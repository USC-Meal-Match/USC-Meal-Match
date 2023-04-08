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

@WebServlet("/rating")
public class RatingAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		BufferedReader in = req.getReader();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		RatingInput curr = gson.fromJson(in, RatingInput.class);

		// todo: handle the ratings we get from the website
		// testing viablility of this API
		if (Rating.setRating(curr.getRating(), curr.getDininghaID(), curr.getUserID())) {
			// setting status
			resp.setStatus(200);
			resp.setHeader("Access-Control-Allow-Origin: ", "*");
			System.out.println("test case successful");
			resp.setContentType("application/json");
		} else {
			resp.setStatus(400);
			System.out.println("Test case failed");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		RatingInput curr = gson.fromJson(req.getReader(), RatingInput.class);
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin: ", "*");
		String anString = Double.toString(Rating.getRating(curr.getDininghaID()));
		PrintWriter pWriter = resp.getWriter();
		pWriter.print(anString);
		pWriter.flush();
		pWriter.close();
	}
}
