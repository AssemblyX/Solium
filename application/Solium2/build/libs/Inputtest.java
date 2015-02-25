package test;

import static org.junit.Assert.*;
import net.assemblyx.solium.StockReportScreen;
import net.assemblyx.solium.controler.StockReportCalculate;
import net.assemblyx.solium.controler.StockReportParser;

import org.junit.Test;

public class Inputtest {

	@Test
	public void test() {
		StockReportScreen stockReportScreen = new StockReportScreen();
		stockReportScreen.screenInput(this.getClass().getResourceAsStream("input.def"));
	}
	
	@Test
	public void test1a() {
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		StockReportScreen stockReportScreen = new StockReportScreen();
		String strOut = stockReportScreen.screenOutString(calculator.init(parser.init(this.getClass().getResourceAsStream("input1a.def"))));

		String str = "001B,1300.00\n";
		str += "002B,1325.00\n";
		str += "003B,500.00\n";
		assertEquals(str, strOut);
	}
	
	@Test
	public void test1b() {
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		StockReportScreen stockReportScreen = new StockReportScreen();
		String strOut = stockReportScreen.screenOutString(calculator.init(parser.init(this.getClass().getResourceAsStream("input1b.def"))));

		String str = "001B,550.00\n";
		str += "002B,825.00\n";
		str += "003B,0.00\n";
		assertEquals(str, strOut);
	}
	
	@Test
	public void test2a() {
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		StockReportScreen stockReportScreen = new StockReportScreen();
		String strOut = stockReportScreen.screenOutString(calculator.init(parser.init(this.getClass().getResourceAsStream("input2a.def"))));
		
		String str = "001B,825.00\n";
		str += "002B,825.00\n";
		str += "003B,550.00\n";
		assertEquals(str, strOut);
	}
	
	@Test
	public void test2b() {
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		StockReportScreen stockReportScreen = new StockReportScreen();
		String strOut = stockReportScreen.screenOutString(calculator.init(parser.init(this.getClass().getResourceAsStream("input2b.def"))));

		String str = "001B,550.00\n";
		str += "002B,550.00\n";
		str += "003B,550.00\n";
		assertEquals(str, strOut);
	}
	
	@Test
	public void test3a() {
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		StockReportScreen stockReportScreen = new StockReportScreen();
		String strOut = stockReportScreen.screenOutString(calculator.init(parser.init(this.getClass().getResourceAsStream("input3a.def"))));
		
		String str = "001B,412.50,275.00\n";
		str += "002B,825.00\n";
		assertEquals(str, strOut);
	}
	
	@Test
	public void test3b() {
		StockReportParser parser = new StockReportParser();
		StockReportCalculate calculator = new StockReportCalculate();
		StockReportScreen stockReportScreen = new StockReportScreen();
		String strOut = stockReportScreen.screenOutString(calculator.init(parser.init(this.getClass().getResourceAsStream("input3b.def"))));

		String str = "001B,275.00,275.00\n";
		str += "002B,550.00\n";
		assertEquals(str, strOut);
	}

}
