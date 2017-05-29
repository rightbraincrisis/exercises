package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import exercise.exception.InsufficientElementsException;

abstract class BaseStack {
	
	private Deque<String> stack;
	/**
	 *  Deque is chosen because it gives true functionality of 
	 *  stack using Linkedlist. The class Stack<E> provided by java is just an 
	 *  extension of Vector.
	 */
	
	private static final String CLEAR = "clear";
	private static final String UNDO = "undo";
	
	BaseStack(){
		stack = new LinkedList<>();
	}
	


	public void push(String bd){
		stack.push(bd);
	}

	public String pop() throws InsufficientElementsException{
		if(stack.isEmpty()) throw new InsufficientElementsException(ConfigurationVariables.getString("EXCEPTION.insufficientStack")); 
		return stack.pop();
	}

	public void clear(){
		stack.clear();
	}

	public void display(){
		System.out.print("stack: "); 
		List<String> list = new ArrayList(stack);
		Collections.reverse(list);
		for (String bd: list){
			System.out.print(bd + " "); 
		}

	}

	public void executeCommand(String element) {
		switch (element){
		case CLEAR: stack.clear();break; 
		default: // configuration never allow this to occur 		
		}
	}
	
	public boolean isCommand(String command) {
		//Extension possible: externalisation of commands
		String[] operators = { CLEAR };
		if (Arrays.asList(operators).contains(command))
			return true;
		return false;
	}
	
	protected Deque<String> getStack() {
		return stack;
	}

	protected void setStack(Deque<String> stack) {
		// cloning
		stack.forEach(element->this.stack.add(element));
	}

	public boolean isUndoCommand(String element) {		
		return  element.equals(UNDO);
	}
	
	public boolean isClearCommand(String element) {
		return  element.equals(CLEAR);
	}
}
