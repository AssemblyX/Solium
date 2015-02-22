package net.assemblyx.solium;

public class Tools {
	
	public static int getNumeric(String str){
		int number = 0;
		if(isNumeric(str) == true)number = Integer.parseInt(str);
		return number;
	}
	
	public static boolean isNumeric(String str)	{
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
