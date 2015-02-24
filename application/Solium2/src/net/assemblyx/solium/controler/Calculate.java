/*
 * Copyright (c) 2015, AssemblyX and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of AssemblyX or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package net.assemblyx.solium.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import net.assemblyx.solium.model.EmployeeRecords;
import net.assemblyx.solium.model.Record;
import net.assemblyx.solium.model.StockReport;

/**
 * 
 * Calculate Class used for all calculations 
 * computes the data stored in a StockReport Model
 * 
 * @author Kenneth Desormeaux
 *
 */

public class Calculate {
	/**
	 * Maps used for storing each employees
	 * records for Vest, Sale and Perf
	 */
	private Map<Integer, VestSales> mVest;
	private Map<Integer, VestSales> mSale;
	private Map<Integer, Double> mPerf;
	/**
	 * Date Cut off and Market price required for calculation
	 */
	private int mDateCut;
	private double mMarketPrice;
	
	/**
	 * Creates a recordBreakup model
	 * 
	 * Loops the stockReport and performs calculations
	 * depending on the record type
	 * 
	 * @param stockReports
	 * @return
	 */
	public StockReport init(StockReport stockReports){
		/**
		 * set the cut off date and market price for calculations
		 */
		mDateCut = stockReports.getDateCut();
		mMarketPrice = stockReports.getMarketPrice();
		/**
		 * loop through each employee found in the stockReport
		 * and get all the records that belong to them
		 */
		for(Map.Entry<String, EmployeeRecords> entry : stockReports.getEmployeesRecords().entrySet()){
			/**
			 * get the employeeRecords model containing all
			 * the records for the employee and set the 
			 * maps for each record
			 */
			EmployeeRecords employeeRecords = entry.getValue();
			RecordBreakup recordBreakup = setMaps(employeeRecords);
			/**
			 * by using TreeMap we sort Vest by date
			 * making it easy to subtract from the earliest Vest 
			 * when there are sales
			 */
			recordBreakup.setVest(new TreeMap<Integer, VestSales>(recordBreakup.getVest()));
			/**
			 * check if there are any records for sales
			 * if so subtract the sales and return the total 
			 * money made from the sales place it in the employeeRecords
			 */
			if(recordBreakup.getSale().size()>0)
				employeeRecords.setSales(subtractSales(recordBreakup));
			/**
			 * check if there are performance records
			 * if so calculate and make adjustments
			 */
			if(recordBreakup.getPerf().size()>0)
				multiplyPerformance(recordBreakup);
			/**
			 * calculate the remaining Vest records
			 */
			employeeRecords.setVest(calculateMain(recordBreakup));
		}
		return stockReports;
	}
	
	/**
	 * Calculates all the Vest records found in the 
	 * recordBreakup model
	 * 
	 * @param recordBreakup
	 * @return
	 */
	private double calculateMain(RecordBreakup recordBreakup) {
		double total = 0;
		for(Map.Entry<Integer, VestSales> entry : recordBreakup.getVest().entrySet()){
			/**
			 * Loop all Vest records and perform calculations
			 * if they are before the cut off date
			 */
			if(mDateCut>=entry.getKey())
				total += (mMarketPrice - entry.getValue().price) * entry.getValue().units;
		}
		return total;
	}

	/**
	 * Calculates and makes adjustments for all the 
	 * performance records found in the recordBreak model
	 * This feature stacks any performance dates
	 * 
	 * @param recordBreakup
	 */
	private void multiplyPerformance(RecordBreakup recordBreakup) {
		for(Map.Entry<Integer, Double> entryPerf : recordBreakup.getPerf().entrySet()){
			for(Map.Entry<Integer, VestSales> entryVest : recordBreakup.getVest().entrySet()){
				/**
				 * make sure the date is only multiplied earlier and before cut off date
				 */
				if(entryPerf.getKey()>=entryVest.getKey() && mDateCut >= entryPerf.getKey()){
					entryVest.getValue().units *= entryPerf.getValue();
				}
			}
		}
	}

	/**
	 * removes and calculates any sold stock
	 * it removes stock from the earliest
	 * 
	 * @param recordBreakup
	 * @return
	 */
	private double subtractSales(RecordBreakup recordBreakup){
		double totalSales = 0;
		/**
		 * loop all the records for sales
		 */
		for(Map.Entry<Integer, VestSales> entrySale : recordBreakup.getSale().entrySet()){
			int totalSold = entrySale.getValue().units;
			/**
			 * loops all the available Vest to subtract from 
			 */
			for(Map.Entry<Integer, VestSales> entryVest : recordBreakup.getVest().entrySet()){
				/**
				 * if vest is higher than amount to subtract 
				 * subtract amount from Vest and goto next record
				 */
				if((entryVest.getValue().units - totalSold)>0){
					entryVest.getValue().units -= totalSold;
					totalSales += calculateSales(totalSold, entrySale.getValue().price, entryVest.getValue().price);
					break;
				/**
				 * if amount to subtract is higher than vest
				 * subtract vest from amount and set vest units to 0
				 * continue to next vest
				 */
				}else{
					totalSold -= entryVest.getValue().units;
					totalSales += calculateSales(entryVest.getValue().units, entrySale.getValue().price, entryVest.getValue().price);
					entryVest.getValue().units = 0;
				}
			}
		}
		/**
		 * returns the total amount made from sales
		 */
		return totalSales;
	}
	
	/**
	 * used to calculate money made off a sale
	 * 
	 * @param units
	 * @param price
	 * @param vest
	 * @return
	 */
	private double calculateSales(int units, double price, double vest){
		return (price - vest) * units;
	}
	
	/**
	 * Sets the RecordBreakup model used for each employee
	 * 
	 * @param employeeStocks
	 * @return
	 */
	private RecordBreakup setMaps(EmployeeRecords employeeStocks){
		/**
		 * put all the records into an ArrayList
		 * and reset all the variables to new
		 */
		ArrayList<Record> records = employeeStocks.getRecords();
		RecordBreakup recordBreakup = new RecordBreakup();
		for(Record record : records){
			switch(record.getType()){
			case Parser.TYPE_VEST:
				VestSales vest = new VestSales();
				vest.units = record.getUnits();
				vest.price = record.getPrice();
				recordBreakup.putVest(record.getDate(), vest);
				break;
			case Parser.TYPE_SALE:
				VestSales sale = new VestSales();
				sale.units = record.getUnits();
				sale.price = record.getPrice();
				recordBreakup.putSales(record.getDate(), sale);
				break;
			case Parser.TYPE_PERF:
				recordBreakup.putPref(record.getDate(), record.getMultiply());
				break;
			}
		}
		return recordBreakup;
	}
	
	/**
	 * Class Model used to breakup the records for each Employee
	 * 
	 * @author Kenneth Desormeaux
	 *
	 */
	class RecordBreakup{
		private Map<Integer, VestSales> mVest = new HashMap<Integer, VestSales>();
		private Map<Integer, VestSales> mSale = new HashMap<Integer, VestSales>();
		private Map<Integer, Double> mPerf = new HashMap<Integer, Double>();
		
		public void putVest(int date, VestSales vest){
			mVest.put(date, vest);
		}
		
		public void setVest(Map<Integer, VestSales> vest){
			mVest = vest;
		}
		
		public void putSales(int date, VestSales sale){
			mSale.put(date, sale);
		}
		
		public void putPref(int date, double multiply){
			mPerf.put(date, multiply);
		}
		
		public Map<Integer, VestSales> getVest(){
			return mVest;
		}
		
		public Map<Integer, VestSales> getSale(){
			return mSale;
		}
		
		public Map<Integer, Double> getPerf(){
			return mPerf;
		}
	}
	
	/**
	 * Model used to store values for Vest and Sales
	 * 
	 * @author Kenneth Desormeaux
	 *
	 */
	class VestSales{
		public int units;
		public double price;
	}
}
