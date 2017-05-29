package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exercise.exception.CustomException;
import exercise.exception.InsufficientElementsException;
import exercise.exception.InvalidOperatorOrEquation;

public class Calculator {
	private AbstractFactory factory;
	private BaseStack stack;
	private BaseMathProcessor mathProcessor; 
	private Caretaker caretaker;
	int i; // processed element counter only required for error message
	
	public Calculator(DataType dataType) {
		factory = AbstractFactory.getFactory(dataType);
        stack = factory.createStack();
        mathProcessor = factory.createMathProcessor();
        caretaker = new Caretaker();
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
						" (position: " + (ordinalIndexOf(line, " ", i)+2) +
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
				createMemento();				
			} else if (mathProcessor.isOperatorBinary(element)) {
				String operand2 = stack.pop();
				String operand1 = stack.pop();
				stack.push(mathProcessor.performBinaryOperation(element, operand1, operand2));
				createMemento();
			} else if (stack.isUndoCommand(element)) {
				stack = caretaker.getMemento();
			} else if (stack.isCommand(element)) {
				stack.executeCommand(element);
			}else if (mathProcessor.isValidOperand(element)) {			
				stack.push(element);
				createMemento();
			} else {
				throw new CustomException(element);
			}
		} catch (InsufficientElementsException e) {
			stack = caretaker.getLatestMemento();
			throw new CustomException(element);
			
		}
		i++;
	}

   private void createMemento() {
		BaseStack newStack = factory.createStack();
		newStack.setStack(stack.getStack());
		caretaker.addMemento(newStack);
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
