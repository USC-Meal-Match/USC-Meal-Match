/*
Coded by Ken Xu, Joey Yap
04/06/2023 :: UPDATED 04/09/2023
*/
package usc_mealmatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;

public class Menus {
	@SerializedName("menus")
	ArrayList<DiningHall> menus;

	public static Menus getMenus() {
		try {
			Menus menus = DatabaseClient.useDatabase((connection, preparedStatement, resultSet) -> {
				try {
					preparedStatement = connection.prepareStatement(
							"SELECT * FROM menu m JOIN dining_halls d ON m.dining_hall_id = d.dining_hall_id ORDER BY m.dining_hall_id");

					resultSet = preparedStatement.executeQuery();

					ArrayList<DiningHall> menuList = new ArrayList<>();

					DiningHall currDiningHall = null;
					while (resultSet.next()) {
						String itemName = resultSet.getString("item_name");
						int diningHallID = resultSet.getInt("dining_hall_id");
						List<String> dietRstrs = Arrays.asList(resultSet.getString("diet_restrictions").split(","));

						if (currDiningHall == null || currDiningHall.getDiningHallID() != diningHallID) {
							String diningHallName = resultSet.getString("dining_hall_name");
							currDiningHall = new DiningHall(diningHallID, diningHallName, new ArrayList<>());
							menuList.add(currDiningHall);
						}

						currDiningHall.addToMenu(new MenuItem(itemName, dietRstrs));
					}

					return new Menus(menuList);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return null;
				}
			});

			return menus;
		} catch (SQLException e) {
			return null;
		}
	}

	public Menus(ArrayList<DiningHall> menus) {
		this.menus = menus;
	}

	public int getSize() {
		return menus.size();
	}

	public void addDiningHall(DiningHall event) {
		menus.add(event);
	}

	public DiningHall getDiningHall(int id) {
		Optional<DiningHall> diningHall = menus.stream().filter((it -> it.getDiningHallID() == id)).findFirst();
		return diningHall.orElse(null);
	}

	public List<DiningHall> getDiningHalls() {
		return menus;
	}
}
