package net.assemblyx.solium;

import java.util.ArrayList;

public class Employee {
	private String mEmployeeId;
	private ArrayList<Record> mRecords;
	private String mSales;
	private String mVests = "";
	
	Employee(){
		mRecords = new ArrayList<Record>();
	}
	
	public void setEmployeeId(String employeeId){
		mEmployeeId = employeeId;
	}
	
	public void setSales(double sales){
		mSales = String.format("%.2f", sales);
	}
	
	public void setVest(double vests) {
		mVests = String.format("%.2f", vests); 
		
	}
	
	public String getEmployeeId(){
		return mEmployeeId;
	}
	
	public ArrayList<Record> getRecords(){
		return mRecords;
	}
	
	public void addRecord(Record record){
		mRecords.add(record);
	}
	
	public String returnReport(){
		String str = mEmployeeId + "," + mVests;
		if(mSales != null)str += "," + mSales;
		return str;
	}


}
