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

@WebServlet("/DishPreference")
public class DishPreferenceListAPI extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("application/json");
		//BufferedReader in = req.getReader();
		//String json = in.readLine();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		DishPreferenceList curr = gson.fromJson(req.getReader(), DishPreferenceList.class);
		
		//todo: handle the dish preference list from the browser
		//testing viablility of this API
		if(curr.getSize() == 3)
		{
			//setting status
			resp.setStatus(200);
			System.out.println("test case successful");
			resp.setContentType("application/json");
			PrintWriter pw = resp.getWriter();
			System.out.println(curr.getDish(0));
			pw.print("{\"XX\": \"Sup\"}");
			pw.flush();
		}
		else
		{
			resp.setStatus(400);
			System.out.println("Test case failed");
		}
	}
}
