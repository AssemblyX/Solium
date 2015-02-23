package net.assemblyx.solium;

import java.util.ArrayList;

public class Employee {
	private String mEmployeeId;
	private ArrayList<VestSale> mVest;
	private ArrayList<VestSale> mSale;
	private ArrayList<Perf> mPerf;

	
	Employee(){
		mVest = new ArrayList<VestSale>();
		mSale = new ArrayList<VestSale>();
		mPerf = new ArrayList<Perf>();
	}
	
	public void setEmployeeId(String employeeId){
		mEmployeeId = employeeId;
	}
	
	public String getEmployeeId(){
		return mEmployeeId;
	}
	
	public void addVest(String[] split2){
		VestSale vestSale = new VestSale();
	}
}
