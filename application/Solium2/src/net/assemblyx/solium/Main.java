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

package net.assemblyx.solium;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import net.assemblyx.solium.controler.Calculate;
import net.assemblyx.solium.controler.Parser;
import net.assemblyx.solium.model.EmployeeRecords;
import net.assemblyx.solium.model.StockReport;

/**
 * 
 * Main Class used for creating a StockReport System.in 
 * and printing StockReport System.out
 * 
 * @author Kenneth Desormeaux
 *
 */

public class Main {
	
	/**
	 * default method used to start the .jar file
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.init(System.in);
	}
	
	/**
	 * starts the process by parsing, calculating and then printing 
	 * data form the input stream
	 * @param inputStream
	 */
	public void init(InputStream inputStream){
		Parser parser = new Parser();
		Calculate calculator = new Calculate();
		/**
		 * Sends InputStream input.def to parser then passes
		 * then sends the result from the parser through calculator
		 */
		StockReport stockReports = calculator.init(parser.init(inputStream));
		/**
		 * sends the result from calculator to print out
		 */
		printScreen(stockReports);
	}
	
	/**
	 * loops all the employees and prints their data
	 * @param stockReport
	 */
	public void printScreen(StockReport stockReport){
		/**
		 * gets all the employees in the report
		 */
		Map<String, EmployeeRecords> employeesStocks = new TreeMap<String, EmployeeRecords>(stockReport.getEmployeesRecords());
		/**
		 * loop though each employee and print out their calculations on a new line 
		 */
		for(Map.Entry<String, EmployeeRecords> entry : employeesStocks.entrySet()){
			System.out.println(entry.getValue().toString());
		}
	}
}
