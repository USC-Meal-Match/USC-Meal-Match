package usc_mealmatch;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dish-preference")
public class DishPreferenceListAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin: ", "*");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		DishPreferenceList curr = gson.fromJson(req.getReader(), DishPreferenceList.class);
		UserProfile profile = UserProfile.getUserProfile(curr.getUserID());

		// setting the user's dish preference list to the list curr acquired
		if (profile.setPref(curr.getDishList())) {
			// setting status
			resp.setStatus(200);
		} else {
			resp.setStatus(400);
		}
	}
}
