package net.assemblyx.solium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Calculate {
	private Map<Integer, VestSales> mVest;
	private Map<Integer, VestSales> mSale;
	private Map<Integer, Double> mPerf;
	private int mDateCut;
	private double mMarketPrice;
	
	Calculate(Parser parser) {
		Map<String, Employee> employees = parser.employees;
		mDateCut = parser.dateCut;
		mMarketPrice = parser.marketPrice;
		mVest = new HashMap<Integer, VestSales>(); 
		for(Map.Entry<String, Employee> entry : employees.entrySet()){
			Employee employee = entry.getValue();
			setMaps(employee);
			//sort mVest by key (Date)
			mVest = new TreeMap<Integer, VestSales>(mVest);
			if(mSale.size()>0)
				employee.setSales(subtractSales());
			if(mPerf.size()>0)
				multiplyPerformance();
			employee.setVest(calculateMain());
		}
	}
	
	private double calculateMain() {
		double total = 0;
		for(Map.Entry<Integer, VestSales> entry : mVest.entrySet()){
			if(mDateCut>=entry.getKey())
			total += (mMarketPrice - entry.getValue().price) * entry.getValue().units;
		}
		return total;
	}

	private void multiplyPerformance() {
		for(Map.Entry<Integer, Double> entryPerf : mPerf.entrySet()){
			for(Map.Entry<Integer, VestSales> entryVest : mVest.entrySet()){
				if(entryPerf.getKey()>=entryVest.getKey() && mDateCut >= entryPerf.getKey()){
					entryVest.getValue().units *= entryPerf.getValue();
				}
			}
		}
	}

	private double subtractSales(){
		double totalSales = 0;
		for(Map.Entry<Integer, VestSales> entrySale : mSale.entrySet()){
			int totalSold = entrySale.getValue().units;
			for(Map.Entry<Integer, VestSales> entryVest : mVest.entrySet()){
				if((entryVest.getValue().units - totalSold)>0){
					entryVest.getValue().units -= totalSold;
					totalSales += calculateSales(totalSold, entrySale.getValue().price, entryVest.getValue().price);
					break;
				}else{
					totalSold -= entryVest.getValue().units;
					totalSales += calculateSales(entryVest.getValue().units, entrySale.getValue().price, entryVest.getValue().price);
					entryVest.getValue().units = 0;
				}
			}
		}
		return totalSales;
	}
	
	private double calculateSales(int units, double price, double vest){
		return (price - vest) * units;
	}
	
	private void setMaps(Employee employee){
		ArrayList<Record> records = employee.getRecords();
		mVest = new HashMap<Integer, VestSales>();
		mSale = new HashMap<Integer, VestSales>();
		mPerf = new HashMap<Integer, Double>();
		for(Record record : records){
			switch(record.getType()){
			case Parser.TYPE_VEST:
				VestSales vest = new VestSales();
				vest.units = record.getUnits();
				vest.price = record.getPrice();
				mVest.put(record.getDate(), vest);
				break;
			case Parser.TYPE_SALE:
				VestSales sale = new VestSales();
				sale.units = record.getUnits();
				sale.price = record.getPrice();
				mSale.put(record.getDate(), sale);
				break;
			case Parser.TYPE_PERF:
				mPerf.put(record.getDate(), record.getMultiply());
				break;
			}
		}
	}
	
	class VestSales{
		public int units;
		public double price;
	}
}
