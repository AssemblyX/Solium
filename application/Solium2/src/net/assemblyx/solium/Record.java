package net.assemblyx.solium;

public class Record {
	private String mType;
	private int mDate;
	private int mUnits;
	private double mPrice;
	private double mMultiply;
	
	public void setType(String type){
		mType = type;
	}
	
	public void setDate(int date){
		mDate = date;
	}

	public void setUnits(int units){
		mUnits = units;
	}
	
	public void setPrice(double price){
		mPrice = price;
	}
	
	public void setMulitply(double multiply){
		mMultiply = multiply;
	}
	
	public String getType(){
		return mType;
	}
	
	public int getDate(){
		return mDate;
	}
	
	public int getUnits(){
		return mUnits;
	}
	
	public double getPrice(){
		return mPrice;
	}
	
	public double getMultiply(){
		return mMultiply;
	}
}
