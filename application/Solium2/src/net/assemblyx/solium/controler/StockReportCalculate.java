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

import java.util.Map;
import java.util.Map.Entry;
import net.assemblyx.solium.model.EmployeeRecords;
import net.assemblyx.solium.model.Record;
import net.assemblyx.solium.model.StockReport;

/**
 * Calculations done on a StockReport Module
 * 
 * @author Kenneth Desormeaux
 */

public class StockReportCalculate {
	/**
	 * Loops the stockReport and performs calculations
	 * 
	 * @param stockReports
	 * @return
	 */
	public StockReport init(StockReport stockReports){
		/**
		 * set the cut off date and market price for calculations
		 */
		int dateCut = stockReports.getDateCut();
		double marketPrice = stockReports.getMarketPrice();
		/**
		 * loop through each employee
		 */
		for(Map.Entry<String, EmployeeRecords> employeesRecords : stockReports.getEmployeesRecords().entrySet()){
			/**
			 * pull out the employee Records
			 */
			EmployeeRecords employeeRecords = employeesRecords.getValue();
			/**
			 * Sort vests by date
			 */
			employeeRecords.sortVests();
			/**
			 * check if there are any records for sales
			 */
			if(employeeRecords.getSales().size()>0)
				employeeRecords.setTotalSales(calculateSales(employeeRecords.getVests(), employeeRecords.getSales(), dateCut));
			/**
			 * check if there are performance records
			 */
			if(employeeRecords.getPerfs().size()>0)
				calculatePerformance(employeeRecords.getVests(), employeeRecords.getPerfs(), dateCut);
			/**
			 * calculate Vest records
			 */
			employeeRecords.setTotalVests(calculateVests(employeeRecords.getVests(), marketPrice, dateCut));
		}
		return stockReports;
	}
	/**
	 * Calculates Vest records 
	 * 
	 * @param map
	 * @return
	 */
	public double calculateVests(Map<Integer, Record> vestRecords, double marketPrice, int dateCut) {
		double total = 0;
		for(Map.Entry<Integer, Record> record : vestRecords.entrySet()){
			/**
			 * Loop all Vest records and perform calculations
			 * if they are before or equal to cut off date
			 */
			if(dateCut>=record.getKey())
				total += (marketPrice - record.getValue().getPrice()) * record.getValue().getUnits();
		}
		return total;
	}
	/**
	 * Calculates and makes adjustments for all performance records
	 * This feature stacks
	 * 
	 * @param employeeRecords
	 */
	public void calculatePerformance(Map<Integer, Record> vestRecords, Map<Integer, Record> perfRecords, int dateCut) {
		for(Entry<Integer, Record> entryPerf : perfRecords.entrySet()){
			for(Entry<Integer, Record> entryVest : vestRecords.entrySet()){
				/**
				 * make sure the date is only multiplied earlier or equal to record date and before or
				 * equal to cut off date
				 */
				if(entryPerf.getKey()>=entryVest.getKey() && dateCut >= entryPerf.getKey()){
					entryVest.getValue().setUnits(
							(int) Math.round(entryVest.getValue().getUnits() * entryPerf.getValue().getMultiply()));
				}
			}
		}
	}
	/**
	 * removes and calculates any sold stock from earliest owned stock
	 * 
	 * @param employeeRecords
	 * @return
	 */
	public double calculateSales(Map<Integer, Record> vestRecords, Map<Integer, Record> salesRecords, int dateCut){
		double totalSales = 0;
		/**
		 * loop all the records for sales
		 */
		for(Map.Entry<Integer, Record> entrySale : salesRecords.entrySet()){
			if(dateCut >= entrySale.getKey()){
				int totalSold = entrySale.getValue().getUnits();
				/**
				 * loops all the available Vest to subtract from 
				 */
				for(Map.Entry<Integer, Record> entryVest : vestRecords.entrySet()){
					/**
					 * if vest is higher than amount to subtract 
					 * subtract amount from Vest
					 */
					if((entryVest.getValue().getUnits() - totalSold)>=0){
						System.out.println(entryVest.getValue().getUnits() - totalSold);
						entryVest.getValue().setUnits(entryVest.getValue().getUnits() - totalSold);
						totalSales += calculateSales(totalSold, entrySale.getValue().getPrice(), entryVest.getValue().getPrice());
						break;
					/**
					 * if amount to subtract is higher than vest
					 * subtract vest from amount and set vest units to 0
					 */
					}else{
						totalSold -= entryVest.getValue().getUnits();
						totalSales += calculateSales(entryVest.getValue().getUnits(), entrySale.getValue().getPrice(), entryVest.getValue().getPrice());
						entryVest.getValue().setUnits(0);
					}
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
}
