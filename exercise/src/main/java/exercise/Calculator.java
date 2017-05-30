package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exercise.exception.CustomException;
import exercise.exception.InsufficientElementsException;
import exercise.exception.InvalidOperatorOrEquation;

public class Calculator {
	private AbstractFactory factory;
	private BaseMathProcessor mathProcessor; 
	private Caretaker caretaker;
	int i; // processed element counter only required for error message
	
	public Calculator(DataType dataType) {
		factory = AbstractFactory.getFactory(dataType);
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
		
		if(caretaker.getLatestMemento() != null) 
			caretaker.getLatestMemento().display();
		else
			System.out.print("stack: ");
		
		return result;
	}

	
	private void processElement(String element) throws CustomException, InvalidOperatorOrEquation {
		
		try {			
			
			BaseStack stack = factory.createStack();
			if(caretaker.size() > 0)
				stack.setStack(caretaker.getLatestMemento().getStack());
			
			if (mathProcessor.isOperatorUnary(element)) {
				stack.push(mathProcessor.performUnaryOperation(element, stack.pop()));	
				caretaker.pushMemento(stack);				
			} else if (mathProcessor.isOperatorBinary(element)) {
				String operand2 = stack.pop();
				String operand1 = stack.pop();
				stack.push(mathProcessor.performBinaryOperation(element, operand1, operand2));
				caretaker.pushMemento(stack);
			} else if (caretaker.isUndoCommand(element)) {
				caretaker.popMemento();
			} else if (caretaker.isClearCommand(element)) {
				caretaker.clear();
			}else if (mathProcessor.isValidOperand(element)) {			
				stack.push(element);
				caretaker.pushMemento(stack);
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
