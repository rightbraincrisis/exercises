package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {   

		DataType dataType = DataType.BIGDECIMAL;
		if(args.length > 0){
			switch (args[0]){
				case "DOUBLE": dataType = DataType.DOUBLE; break;
				case "BIGDECIMAL": dataType = DataType.BIGDECIMAL; break;
				//Extension:  Usage message
			}
		}
		Calculator calculator = new Calculator(dataType);
		try {
			boolean canContinue = true;
			while (canContinue ) {
				System.out.println("");
				String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
				canContinue = calculator.process(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
