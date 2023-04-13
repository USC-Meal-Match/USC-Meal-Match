/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin: ", "*");

		// getting user input from the browser
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		DietPreferenceList curr = gson.fromJson(req.getReader(), DietPreferenceList.class);
		UserProfile profile = UserProfile.getUserProfile(curr.getUserID());

		// Setting the diet restriction to the list acquired by curr
		if (profile.setDietRstr(curr.getDietList())) {
			// setting status
			resp.setStatus(200);
		} else {
			resp.setStatus(400);
		}
	}
}
