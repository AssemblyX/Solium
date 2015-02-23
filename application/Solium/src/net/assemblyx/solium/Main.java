package net.assemblyx.solium;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Main {
	public static final String TYPE_VEST = "VEST";
	public static final String TYPE_PERF = "PERF";
	public static final String TYPE_SALE = "SALE";
	
	private JSONObject mJSONObject = new JSONObject();
	private ArrayList<String> mListSort = new ArrayList<String>();
	
	public static void main(String[] args) {
		Main main = new Main();
		main.loopInput(System.in);
	}
	
	public void loopInput(InputStream inputStream){
		int counter = 0;
		int recordCount = 0;
		int lineCount = 0;
		Scanner scanner = new Scanner(inputStream);
		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				counter++;
				if(counter > 1 && counter < lineCount){
					setListSort(line);
					System.out.println("Loop");
				}else if(counter == 1){
					recordCount = Tools.getNumeric(line);
					lineCount = recordCount+2;
					mJSONObject.put("recordCount", recordCount);
				}else{
					String[] split = line.split(",");
					mJSONObject.put("dateCut", split[0]);
					mJSONObject.put("marketPrice", split[1]);
				}
			 }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		scanner.close();
		loopSortList();
	}
	
	//Put date first for sorting
	// Change type to number
	private void setListSort(String line){
		String[] split = line.split(",");
		//set three constants, Date, Type, Employee
		String sort = split[2] + "," + split[0] + "," + split[1];
		//add the rest to the end since not all are constant
		for(int i=3; i<split.length;i++){
			sort += "," + split[i];
		}
		mListSort.add(sort);
	}

	private void loopSortList() {
		Collections.sort(mListSort);
		ArrayList<String> employeeList = new ArrayList<String>();
		try {
			mJSONObject.put("employees", new JSONArray());
			for(int i=0; i<mListSort.size();i++){
				String[] split = mListSort.get(i).split(",");
				//check if we have employee in json object
				if(!employeeList.contains(split[2])){
					//add employee to list and create employee in json object
					employeeList.add(split[2]);
					setEmployeeObject(split[2]);
				}
				addEmployeeRecord(employeeList.indexOf(split[2]), split);
			}
			System.out.println(mJSONObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addEmployeeRecord(int index, String[] record){
		JSONObject jObject = new JSONObject();
		try{
			JSONObject jemployee = (JSONObject) mJSONObject.getJSONArray("employees").get(index);
			switch(record[1]){
			case TYPE_VEST:
				jObject.put("dateVest", record[0]);
				jObject.put("units", record[3]);
				jObject.put("grantPrice", record[4]);
				jemployee.getJSONArray(TYPE_VEST).put(jObject);
				break;
			case TYPE_PERF:
				jObject.put("dateEffect", record[0]);
				jObject.put("multiplier", record[3]);
				jemployee.getJSONArray(TYPE_PERF).put(jObject);
				break;
			case TYPE_SALE:
				jObject.put("dateSale", record[0]);
				jObject.put("amount", record[3]);
				jObject.put("marketPrice", record[4]);
				jemployee.getJSONArray(TYPE_SALE).put(jObject);
				break;

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Create new employee object
	private void setEmployeeObject(String employee) {
		JSONObject JSONemployee = new JSONObject();
		try {
			JSONemployee.put("employeeId", employee);
			JSONemployee.put(TYPE_VEST, new JSONArray());
			JSONemployee.put(TYPE_PERF, new JSONArray());
			JSONemployee.put(TYPE_SALE, new JSONArray());
			mJSONObject.getJSONArray("employees").put(JSONemployee);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
