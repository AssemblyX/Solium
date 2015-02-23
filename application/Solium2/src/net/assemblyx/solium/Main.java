package net.assemblyx.solium;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static final String TYPE_VEST = "VEST";
	public static final String TYPE_PERF = "PERF";
	public static final String TYPE_SALE = "SALE";
	
	private int mDateCut;
	private double mMarketPrice;
	private Map<String, Employee> mEmployees = new HashMap<String, Employee>();
	public static void main(String[] args) {
		Main main = new Main();
		main.loopInput(System.in);
	}
	
	public void loopInput(InputStream inputStream){
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
				mDateCut = Integer.parseInt(split[0]);
				mMarketPrice = Double.parseDouble(split[1]);
			}
		 }
		scanner.close();
		System.out.println(mEmployees);
	}
	
	private void addRecord(String record){
		String[] split = record.split(",");
		if(!mEmployees.containsKey(split[1])){
			mEmployees.put(split[1], new Employee());
		}
		
		switch(split[0]){
		case TYPE_VEST:
			mEmployees.get(split[1]).addVest(split);
			break;
		case TYPE_PERF:
			break;
		case TYPE_SALE:
			break;
		}
		
	}
}
