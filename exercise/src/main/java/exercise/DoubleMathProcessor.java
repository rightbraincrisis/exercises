package exercise;

import java.util.Arrays;

import exercise.exception.InvalidOperatorOrEquation;

public class DoubleMathProcessor extends BaseMathProcessor {

	@Override
	public String getMethodName(String op) {
		// Cannot use because you cannot overload binary operators like + - *, etc.
		// hence going with switch case in the following method.
		return op;
	}

	@Override
	public String performBinaryOperation(String operator, String s1, String s2) throws InvalidOperatorOrEquation {
		Double d1 = new Double(s1);
		Double d2 = new Double(s2);
		Double result = null;
		switch(operator){
			case "+": result = d1+d2; break;
			case "-": result = d1-d2; break;
			case "*": result = d1*d2; break;
			case "/": 
				if(d2!=0) result = d1/d2; 
				else throw new InvalidOperatorOrEquation("Divide by zero");				 
				break;
			default: 
		}
		return String.valueOf(result);
	}

	@Override
	public String performUnaryOperation(String operator, String s1) throws InvalidOperatorOrEquation {
		Double d1 = new Double(s1);
		Double result = null;
		switch(operator){
			case "sqrt": {
				if(d1<0) throw new InvalidOperatorOrEquation(operator);	
				result = Math.sqrt(d1); break;
			
			}
			case "negate": result = d1 * -1; break;
			default: throw new InvalidOperatorOrEquation("Operator not supported... yet");	
		}
		return String.valueOf(result);
	}

	//Extension: The next 3 methods can be pushed to Base class.
	
	@Override
	public boolean isValidOperand(String element) {
		// To test if the given string is a number to be precise BigDecimal
		// The following dude i.e. NumberFormat doesn't handle 1.3.3, hence writing custom regex
		// Number n = NumberFormat.getNumberInstance().parse(element);
		// the custom regex also handles likes of -0.3 and .345
		
		if(element.matches("^[-]?((\\d+[.]*\\d*)|([.]*\\d+))$")) return true;
		return false;
	}
	
	@Override
	public boolean isOperatorBinary(String operator) {
		String[] operators = { "+", "-", "/", "*" };
		if (Arrays.asList(operators).contains(operator))
			return true;
		return false;
	}

	@Override
	public boolean isOperatorUnary(String operator) {
		String[] operators = { "sqrt", "negate" };
		if (Arrays.asList(operators).contains(operator))
			return true;
		return false;
	}

}
