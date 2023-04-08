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

@WebServlet("/dininghall")
public class DiningHallAPI extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("application/json");
		//BufferedReader in = req.getReader();
		//String json = in.readLine();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Menus curr = gson.fromJson(req.getReader(), Menus.class);
		
		//todo: handle the dininghall info from the browser
		//testing viablility of this API
		if(curr.getDiningHallItem(0).getName().equals("Parkside Dining Hall"))
		{
			//setting status
			resp.setStatus(200);
			System.out.println(curr.getSize());
			resp.setContentType("application/json");
			PrintWriter pw = resp.getWriter();
			System.out.println(curr.getDiningHallItem(1).getItem(2));
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
