package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dininghall")
public class DiningHallAllAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setStatus(200);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Menus menus = Menus.getMenus();
		PrintWriter pw = resp.getWriter();
		String json = gson.toJson(menus);
		pw.print(json);
		pw.flush();
		pw.close();
	}
}
