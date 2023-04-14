/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/12/2023
*/
package usc_mealmatch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dininghall/*")
public class DiningHallAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {
			int id = Integer.parseInt(req.getPathInfo().substring(1));
			Menus menus = Menus.getMenus();

			DiningHall diningHall = menus.getDiningHall(id);
			if (diningHall != null) {
				List<MenuItem> menu = diningHall.getMenu();
				DiningHallItem dItem = new DiningHallItem(diningHall.getName(), id, menu);
				resp.setStatus(200);
				PrintWriter pw = resp.getWriter();
				String json = gson.toJson(dItem);
				pw.print(json);
				pw.flush();
				pw.close();
			} else {
				resp.setStatus(400);
			}
		} catch (NumberFormatException e) {
			resp.setStatus(400);
		}
	}
}
