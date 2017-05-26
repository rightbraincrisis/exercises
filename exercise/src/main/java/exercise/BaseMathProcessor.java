package exercise;

import exercise.exception.InvalidOperatorOrEquation;

abstract class BaseMathProcessor {
	
	public abstract String getMethodName(String op);
	
	public abstract String performBinaryOperation(String operator, String s1, String s2) throws InvalidOperatorOrEquation;
	
	public abstract String performUnaryOperation(String operator, String s1) throws InvalidOperatorOrEquation;

	public abstract boolean isValidOperand(String element);

	public abstract boolean isOperatorBinary(String operator);

	public abstract boolean isOperatorUnary(String operator);

}
