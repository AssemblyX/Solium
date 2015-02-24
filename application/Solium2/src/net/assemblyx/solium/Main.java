package net.assemblyx.solium;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.runapp(System.in);
	}
	
	public void runapp(InputStream inputStream){
		Parser parser = new Parser(inputStream);
		new Calculate(parser);
		printScreen(parser);
	}
	
	public void printScreen(Parser parser){
		Map<String, Employee> employees = new TreeMap<String, Employee>(parser.employees);
		for(Map.Entry<String, Employee> entry : employees.entrySet()){
			System.out.println(entry.getValue().returnReport());
		}
	}
}
