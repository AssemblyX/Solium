package net.assemblyx.solium;

import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONObject;



public class Main {
	JSONObject mJSONObject;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setJSON(System.in);
	}
	
	public void setJSON(InputStream inputStream){
		int counter = 0;
		int recorderCount = 0;
		int lineCount = 0;
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			counter++;
			if(counter > 1 && counter < lineCount){
				setJSONEmployee(line);
				System.out.println("Loop");
			}else if(counter == 1){
				recorderCount = Tools.getNumeric(line);
				lineCount = recorderCount+2;
				System.out.println("Start");
			}else{
				System.out.println("done");
			}
		 }
		scanner.close();
	}

	private void setJSONEmployee(String line) {
		
	}
}
