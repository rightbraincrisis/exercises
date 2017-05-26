package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exercise.exception.CustomException;
import exercise.exception.InsufficientElementsException;
import exercise.exception.InvalidOperatorOrEquation;

public class Calculator {
	private BaseStack stack;
	private BaseMathProcessor mathProcessor; 
	int i; // processed element counter only required for error message
	
	public Calculator(DataType dataType) {
		AbstractFactory factory = AbstractFactory.getFactory(dataType);
        stack = factory.createStack();
        mathProcessor = factory.createMathProcessor();
	}

	public boolean process(String line) {		
		boolean result = true;
		i = 0; 
		// Note the word whitespace and not spaces
		List<String> elements = new ArrayList<String>(Arrays.asList(line.split("\\s")));

		for(String element: elements){
			try {
				processElement(element);
			} catch (CustomException e) {
				System.out.println(
						"Operator " + e.getMessage() +
						"(position: " + (ordinalIndexOf(line, " ", i)+2) +
						"): insufficient parameters");
				result = false; break;
			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				result = false; break;
			}
		}
		stack.display();
		return result;
	}



	private void processElement(String element) throws CustomException, InvalidOperatorOrEquation {
		try {
			if (mathProcessor.isOperatorUnary(element)) {

				stack.push(mathProcessor.performUnaryOperation(element, stack.pop()));

			} else if (mathProcessor.isOperatorBinary(element)) {
				stack.push(mathProcessor.performBinaryOperation(element, stack.pop(), stack.pop()));
			} else if (stack.isCommand(element)) {
				stack.executeCommand(element);
			} else if (mathProcessor.isValidOperand(element)) {
				stack.push(element);
			} else {
				throw new CustomException(element);
			}
		} catch (InsufficientElementsException e) {
			throw new CustomException(element);
		}
		i++;
	}

   /*
    *  This method calculates the position of the invalid element 
    */
	private static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

}
