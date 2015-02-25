package net.assemblyx.solium;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import net.assemblyx.solium.controler.StockReportCalculate;
import net.assemblyx.solium.controler.StockReportParser;
import net.assemblyx.solium.model.EmployeeRecords;
import net.assemblyx.solium.model.StockReport;

public class StockReportScreen {
	public void screenInput(InputStream inputStream){
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		/**
		 * Sends InputStream input.def to parser pass
		 * StockReport through calculator
		 */
		String str = screenOutString(calculator.init(parser.init(inputStream)));
		screenOutput(str);		
	}
	
	public String screenOutString(StockReport stockReport){
		String str = "";
		/**
		 * loop all the employees in the report
		 */
		Map<String, EmployeeRecords> employeesStocks = new TreeMap<String, EmployeeRecords>(stockReport.getEmployeesRecords());
		/**
		 * loop though each employee and print out their calculations on a new line 
		 */
		for(Map.Entry<String, EmployeeRecords> entry : employeesStocks.entrySet()){
			str += entry.getValue().toString() + "\n";
		}
		return str;
	}
	
	public void screenOutput(String stockReportStr){
		System.out.println(stockReportStr);
	}
}
