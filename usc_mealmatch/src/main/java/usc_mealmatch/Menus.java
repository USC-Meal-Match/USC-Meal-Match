package usc_mealmatch;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class Menus 
{
	@SerializedName("menus")
	ArrayList<DiningHallItem> menus;
	
	public int getSize()
	{
		return menus.size();
	}
	
	public void addDiningHallItem(DiningHallItem event)
	{
		menus.add(event);
	}
	
	public DiningHallItem getDiningHallItem(int index)
	{
		return menus.get(index);
	}
		
}
