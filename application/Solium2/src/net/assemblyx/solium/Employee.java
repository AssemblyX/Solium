package net.assemblyx.solium;

import java.util.ArrayList;

public class Employee {
	private String mEmployeeId;
	private ArrayList<Record> mRecords;
	
	Employee(){
		mRecords = new ArrayList<Record>();
	}
	
	public void setEmployeeId(String employeeId){
		mEmployeeId = employeeId;
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
}
