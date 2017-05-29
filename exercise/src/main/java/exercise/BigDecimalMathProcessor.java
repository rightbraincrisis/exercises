package exercise;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise.exception.DivideByZeroException;
import exercise.exception.InvalidNumberException;
import exercise.exception.InvalidOperatorOrEquation;

public class BigDecimalMathProcessor extends BaseMathProcessor {
	
	private static final String CUSTOM = "custom:";
	private static int SCALE = ConfigurationVariables.getNumber("BIGDECIMAL.storageScale");
	private static RoundingMode ROUNDING_MODE = RoundingMode.FLOOR; //Extension possible: to externalise
	private static String SUPPORTED_OPERATIONS = ConfigurationVariables.getString("BIGDECIMAL.supportedBinaryOperations");
	private static String SUPPORTED_UNARY_OPERATIONS = ConfigurationVariables.getString("BIGDECIMAL.supportedUnaryOperations");
	private static String MAP_OPERATOR_METHOD = ConfigurationVariables.getString("BIGDECIMAL.mapBinaryOperations");
	
	private Map<String, String> mapOperatorMethod;
	
	public BigDecimalMathProcessor(){
		loadOperatorMap();
	}
	
	private void loadOperatorMap() {
		mapOperatorMethod = new HashMap<>();
		String [] operators = MAP_OPERATOR_METHOD.split(" ");
		List<String> operatorsList = new ArrayList<>(Arrays.asList(operators));
		operatorsList.forEach(element -> {		
			String [] keyValue = element.split("=");
			mapOperatorMethod.put(keyValue[0], keyValue[1]);			
		});		
	}

	public String getMethodName(String op){
		return mapOperatorMethod.get(op);
	}
	

	@Override
	public String performBinaryOperation(String operator, String s1, String s2) throws InvalidOperatorOrEquation {
		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);
		String methodName = getMethodName(operator);
		BigDecimal result = null;
		try {
	        Method m1 = null;
	        if(methodName.contains(CUSTOM)){
	        	Class<?>[] arguments = {BigDecimal.class, BigDecimal.class};
	        	Object[] parameters = {b1,b2};

	        	methodName = methodName.replace(CUSTOM,"");
	        	m1 = this.getClass().getMethod(methodName, arguments);
	        	result = (BigDecimal) m1.invoke(this, parameters);
	        }else{
	        	m1 = BigDecimal.class.getMethod(methodName, BigDecimal.class);	        	
	        	result = (BigDecimal) m1.invoke(b1, b2);
	        }    
	        	        
	      //Common exception: Because the action of the exception is same.
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
	        if(methodName.contains(CUSTOM)){
	       
	        	methodName = methodName.replace(CUSTOM,"");
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
	    	//Custom common exception because the handling strategy is same.
	    	throw new InvalidOperatorOrEquation(e.getMessage());
	    } 			
		
		result = result.setScale(SCALE, ROUNDING_MODE);
		return result.toPlainString();		
	}
	
	public BigDecimal divider(BigDecimal b1, BigDecimal b2) throws DivideByZeroException{
		if(b1.signum() == 0) return BigDecimal.ZERO;
		if(b2.signum() == 0) throw new DivideByZeroException("Divide by zero"); 
		
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
		String [] operators = SUPPORTED_OPERATIONS.split(" ");
		if (Arrays.asList(operators).contains(operator))
			return true;
		return false;
	}

	@Override
	public boolean isOperatorUnary(String operator) {
		String [] operators = SUPPORTED_UNARY_OPERATIONS.split(" ");
		if (Arrays.asList(operators).contains(operator))
			return true;
		return false;
	}
	
	
	public BigDecimal sqrt(BigDecimal b1) throws InvalidNumberException{
		// handle negative number
		if(b1.signum() == -1) throw new InvalidNumberException("Positive Number expected");
		return BigDecimalSqrtHelper.bigSqrt(b1).setScale(SCALE, ROUNDING_MODE);
	}
}
