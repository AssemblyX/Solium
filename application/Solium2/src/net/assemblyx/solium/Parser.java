package net.assemblyx.solium;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
	public static final int RECORD_TYPE = 0;
	public static final int RECORD_EMPLOYEE = 1;
	public static final int RECORD_DATE = 2;
	public static final int RECORD_UNITS = 3;
	public static final int RECORD_PRICE = 4;
	public static final int RECORD_MULTIPLY = 3;
	public static final String TYPE_VEST = "VEST";
	public static final String TYPE_PERF = "PERF";
	public static final String TYPE_SALE = "SALE";
	
	public int dateCut;
	public double marketPrice;
	public Map<String, Employee> employees = new HashMap<String, Employee>();
	
	public Parser(InputStream inputStream){
		int counter = 0;
		int recordCount = 0;
		int lineCount = 0;
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			counter++;
			if(counter > 1 && counter < lineCount){
				addRecord(line);
			}else if(counter == 1){
				recordCount = Integer.parseInt(line);
				lineCount = recordCount+2;

			}else{
				String[] split = line.split(",");
				dateCut = Integer.parseInt(split[0]);
				marketPrice = Double.parseDouble(split[1]);
			}
		 }
		scanner.close();
	}
	
	private void addRecord(String line){
		Record record = new Record();
		String[] split = line.split(",");
		String employeeId = split[RECORD_EMPLOYEE];
		//check if employee object created
		if(!employees.containsKey(employeeId))
			employees.put(employeeId, new Employee());
		//Set record type and date
		String recordType = split[RECORD_TYPE];
		record.setType(recordType);
		record.setDate(Integer.parseInt(split[RECORD_DATE]));
		//Determine what type of record adding
		switch(recordType){
		case TYPE_VEST:
		case TYPE_SALE:
			record.setUnits(Integer.parseInt(split[RECORD_UNITS]));
			record.setPrice(Double.parseDouble(split[RECORD_PRICE]));
			break;
		case TYPE_PERF:
			record.setMulitply(Double.parseDouble(split[RECORD_MULTIPLY]));
			break;
		}
		employees.get(employeeId).setEmployeeId(employeeId);
		employees.get(employeeId).addRecord(record);
	}
}
