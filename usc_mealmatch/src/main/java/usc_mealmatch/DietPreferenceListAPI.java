package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/preference")
public class DietPreferenceListAPI extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("application/json");
		//getting user input from the browser
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		DietPreferenceList curr = gson.fromJson(req.getReader(), DietPreferenceList.class);
		UserProfile profile = UserProfile.getUserProfile(curr.getUserID());
		
		//Setting the diet restriction to the list acquired by curr
		//testing viablility of this API
		if(profile.setDietRstr(curr.getDietList()))
		{
			//setting status
			resp.setStatus(200);
			System.out.println("test case successful");
			resp.setContentType("application/json");
			System.out.println(curr.getDiet(0));
		}
		else
		{
			resp.setStatus(400);
			System.out.println("Test case failed");
		}
	}
	
	//sending the renewed diet preferrence list to the front end
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		System.out.println("sup");
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin: ", "*");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		DietPreferenceList curr = gson.fromJson(req.getReader(), DietPreferenceList.class);
		String anString = gson.toJson(curr.getDietList());
		PrintWriter pWriter = resp.getWriter();
		pWriter.print(anString);
		pWriter.flush();
		pWriter.close();
	}
}
