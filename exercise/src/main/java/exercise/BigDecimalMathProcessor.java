package exercise;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;

import exercise.exception.DivideByZeroException;
import exercise.exception.InvalidNumberException;
import exercise.exception.InvalidOperatorOrEquation;

public class BigDecimalMathProcessor extends BaseMathProcessor {
/*	public static void main (String[] args){
		BigDecimal bd1 = new BigDecimal("11");
		BigDecimal bd2 = new BigDecimal("2");
		String bd1 = "11";
		String bd2 = "1212";
		String operator = "add";

		BigDecimalMathProcessor processor = new BigDecimalMathProcessor();
		processor.performBinaryOperation("+", bd1, bd2);
		processor.performBinaryOperation("-", bd1, bd2);
		processor.performBinaryOperation("*", bd1, bd2);
		processor.performBinaryOperation("/", bd1, bd2);
		processor.performUnaryOperation("negate", bd1);		
		processor.performUnaryOperation("sqrt", bd2);
		
		
	}
	*/
	
	private static int SCALE = ConfigurationVariables.getNumber("BIGDECIMAL.storageScale");
	private static RoundingMode ROUNDING_MODE= RoundingMode.HALF_UP; //TODO: externalise
	
	public String getMethodName(String op){
		String methodName = null;
		switch (op) {
		case "+":  methodName = "add"; break;
		case "-":  methodName = "subtract"; break;
		case "*":  methodName = "multiply"; break;
		case "/":  methodName = "custom:divider"; break;
		case "negate":  methodName = "negate"; break;
		case "sqrt":  methodName = "custom:sqrt"; break;
		}
		return methodName;
	}
	

	@Override
	public String performBinaryOperation(String operator, String s1, String s2) throws InvalidOperatorOrEquation {
		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);
		String methodName = getMethodName(operator);
		BigDecimal result = null;
		try {
	        Method m1 = null;
	        if(methodName.contains("custom:")){
	        	Class<?>[] arguments = {BigDecimal.class, BigDecimal.class};
	        	Object[] parameters = {b1,b2};

	        	methodName = methodName.replace("custom:","");
	        	m1 = this.getClass().getMethod(methodName, arguments);
	        	result = (BigDecimal) m1.invoke(this, parameters);
	        }else{
	        	m1 = BigDecimal.class.getMethod(methodName, BigDecimal.class);	        	
	        	result = (BigDecimal) m1.invoke(b1, b2);
	        }    
	        	        
	      //TODO: fix the exception of div by zero
	    }  catch (Exception e) {
	        throw new InvalidOperatorOrEquation("No such operation / Divide by zero / Invalid operand");
	    } 
		
		result = result.setScale(SCALE, ROUNDING_MODE);
		return result.toPlainString();
	}
	
	@Override
	public String performUnaryOperation(String operator, String s1) throws InvalidOperatorOrEquation{
		BigDecimal b1 = new BigDecimal(s1);
		String methodName = getMethodName(operator);
		BigDecimal result = null;
		try {
	        Method m1 = null;
	        if(methodName.contains("custom:")){
	       
	        	methodName = methodName.replace("custom:","");
	        	m1 = this.getClass().getMethod(methodName, BigDecimal.class);
	        	result = (BigDecimal) m1.invoke(this, b1);
	        }else{
	        	m1 = BigDecimal.class.getMethod(methodName, null);	        	
	        	result = (BigDecimal) m1.invoke(b1,null);
	        }    
	        //System.out.println(result);
	        //java.lang.NoSuchMethodException

	    }
		catch (Exception e) {
	    	//TODO: don't eat the exceptions fix it
	    	throw new InvalidOperatorOrEquation(e.getMessage());
	    } 			
		return result.toPlainString();		
	}
	
	public BigDecimal divider(BigDecimal b1, BigDecimal b2) throws DivideByZeroException{
		if(b1.signum() == 0) return BigDecimal.ZERO;
		if(b2.signum() == 0) throw new DivideByZeroException("Divide by zero"); //TODO: throw exception
		
		BigDecimal result = b1.divide(b2,SCALE, ROUNDING_MODE);
		return result;
	}
	
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
	
	
	public BigDecimal sqrt(BigDecimal b1) throws InvalidNumberException{
		// TODO: handle negative number
		if(b1.signum() == -1) throw new InvalidNumberException("Positive Number expected");
		return BigDecimalSqrtHelper.bigSqrt(b1).setScale(SCALE, ROUNDING_MODE);
	}
}
