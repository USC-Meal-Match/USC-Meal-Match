package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/DishPreference")
public class DishPreferenceListAPI extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("application/json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		DishPreferenceList curr = gson.fromJson(req.getReader(), DishPreferenceList.class);
		UserProfile profile = UserProfile.getUserProfile(curr.getUserID());
		
		//setting the user's dish preference list to the list curr acquired
		//testing viablility of this API
		if(profile.setPref(curr.getDishList()))
		{
			//setting status
			resp.setStatus(200);
			System.out.println("test case successful");
			resp.setContentType("application/json");
			System.out.println(curr.getDish(0));
		}
		else
		{
			resp.setStatus(400);
			System.out.println("Test case failed");
		}
	}
	
	//sending the renewed dish preferrence list to the front end
		@Override 
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
		{
			System.out.println("sup");
			resp.setContentType("application/json");
			resp.setHeader("Access-Control-Allow-Origin: ", "*");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			DishPreferenceList curr = gson.fromJson(req.getReader(), DishPreferenceList.class);
			String anString = gson.toJson(curr.getDishList());
			PrintWriter pWriter = resp.getWriter();
			pWriter.print(anString);
			pWriter.flush();
			pWriter.close();
		}
}
