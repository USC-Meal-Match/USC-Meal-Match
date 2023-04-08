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
	private DietPreferenceList curr = new DietPreferenceList() ;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("application/json");
		//BufferedReader in = req.getReader();
		//String json = in.readLine();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		curr = gson.fromJson(req.getReader(), DietPreferenceList.class);
		
		//todo: handle the diet preference list from the browser
		//testing viablility of this API
		if(curr.getSize() == 3)
		{
			//setting status
			resp.setStatus(200);
			System.out.println("test case successful");
			resp.setContentType("application/json");
			PrintWriter pw = resp.getWriter();
			System.out.println(curr.getDiet(0));
			String anString = "DietPreference:";
			anString += gson.toJson(curr.getDietList());
			pw.print(anString);
			pw.flush();
			pw.close();
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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String anString = gson.toJson(curr.getDietList());
		PrintWriter pWriter = resp.getWriter();
		pWriter.print(anString);
		pWriter.flush();
		pWriter.close();
	}
}
