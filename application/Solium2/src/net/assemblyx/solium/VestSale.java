package net.assemblyx.solium;

public class VestSale extends Record{
	private int mUnits;
	private double mPrice;
	
	public void setUnits(int units){
		mUnits = units;
	}
	
	public void setPrice(double price){
		mPrice = price;
	}
	
	public int getUnits(){
		return mUnits;
	}
	
	public double getPrice(){
		return mPrice;
	}
}
