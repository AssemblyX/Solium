package net.assemblyx.solium.controler;

public class Tools {
	public static int getInt(String str){
		return (isNumeric(str) == true)?Integer.parseInt(str):0;
	}
	
	public static double getDouble(String str){
		return (isNumeric(str) == true)?Double.parseDouble(str):0;
	}
	
	public static boolean isNumeric(String str)	{
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
