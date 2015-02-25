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

import java.io.InputStream;
import java.util.Scanner;
import net.assemblyx.solium.model.EmployeeRecords;
import net.assemblyx.solium.model.Record;
import net.assemblyx.solium.model.StockReport;

/**
 * 
 * Parser Class used for looping the System.in file 
 * creating an employee object for every employee found in the file
 * 
 * Each employee object stores an ArrayList of every record
 * belonging to them. Depending on the type of record determines 
 * the data being stored in the ArrayList record
 * 
 * @author Kenneth Desormeaux
 *
 */

public class StockReportParser {
	/**
	 * constants used for position in array after splitting
	 * the record using .split(",")
	 */
	public static final int RECORD_DATECUT = 0;
	public static final int RECORD_TYPE = 0;
	public static final int RECORD_MARKETPRICE = 1;
	public static final int RECORD_EMPLOYEE = 1;
	public static final int RECORD_DATE = 2;
	public static final int RECORD_UNITS = 3;
	public static final int RECORD_MULTIPLY = 3;
	public static final int RECORD_PRICE = 4;
	/**
	 * constants used for the different types of records
	 */
	public static final String TYPE_VEST = "VEST";
	public static final String TYPE_PERF = "PERF";
	public static final String TYPE_SALE = "SALE";
	
	/**
	 * Report created from input file
	 */
	private StockReport mStockReport;
	/**
	 * Loops the input stream input.def file
	 * @param inputStream
	 * @return
	 */
	public StockReport init(InputStream inputStream){
		mStockReport = new StockReport();
		/**
		 * counters used to determine if the Scanner is 
		 * on the first line or the last line
		 */
		int counter = 0;
		int lineCount = 0;
		/**
		 * initiate scanner with input.def file
		 */
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			counter++;
			/**
			 * check and make sure we are not first line 
			 * or last line, if not parserRecord
			 */
			if(counter > 1 && counter < lineCount){
				parseRecord(line);
			/**
			 * if on the first line calculate how many lines
			 * there are by adding two to the value of the 
			 * first line
			 */
			}else if(counter == 1){
				lineCount = Integer.parseInt(line)+2;
			/**
			 * if this is the last line set the Date Cut off
			 * and the Market Price for vesting in the StockReport Model
			 */
			}else{
				String[] split = line.split(",");
				mStockReport.setDateCut(Integer.parseInt(split[RECORD_DATECUT]));
				mStockReport.setmMarketPrice(Double.parseDouble(split[RECORD_MARKETPRICE]));
			}
		 }
		scanner.close();
		return mStockReport;
	}
	
	/**
	 * Parsers each record and checks to make sure employee has been added
	 * adds recorded to correct employee and type
	 * @param line
	 */
	private void parseRecord(String line){
		String[] recordSplit = line.split(",");
		String employeeId = recordSplit[RECORD_EMPLOYEE];
		/**
		 * check if the key matching employeeId is found in 
		 * employeesStocks if not create a new EmployeeStocks 
 		 * with employeeId as key
		 */
		
		if(!mStockReport.getEmployeesRecords().containsKey(employeeId)){
			mStockReport.getEmployeesRecords().put(employeeId, new EmployeeRecords());
			mStockReport.getEmployeesRecords().get(employeeId).setEmployeeId(employeeId);
		}
		int recordDate = Integer.parseInt(recordSplit[RECORD_DATE]);
		Record record = createRecord(recordSplit);
		switch(recordSplit[RECORD_TYPE]){
		case TYPE_VEST:
			mStockReport.getEmployeesRecords().get(employeeId).putVests(recordDate, record);
			break;
		case TYPE_SALE:	
			mStockReport.getEmployeesRecords().get(employeeId).putSales(recordDate, record);
			break;
		case TYPE_PERF:
			mStockReport.getEmployeesRecords().get(employeeId).putPerfs(recordDate, record);
			break;
		}
	}
	
	/**
	 * returns Record
	 * @param recordSplit
	 * @return
	 */
	private Record createRecord(String[] recordSplit){
		/**
		 * Start a new Record model
		 */
		Record record = new Record();
		/**
		 * find the record type being added
		 * and add the correct variables for the record
		 * 
		 * VEST and SALE share the same variables since they both
		 * use units and price
		 */
		switch(recordSplit[RECORD_TYPE]){
		case TYPE_VEST:
		case TYPE_SALE:	
			record.setUnits(Tools.getInt(recordSplit[RECORD_UNITS]));
			record.setPrice(Tools.getDouble(recordSplit[RECORD_PRICE]));
			break;
		case TYPE_PERF:
			record.setMulitply(Tools.getDouble(recordSplit[RECORD_MULTIPLY]));
			break;
		}
		return record;
	}
}
