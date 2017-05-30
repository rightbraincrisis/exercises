package exercise;

import java.util.ArrayList;
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

	public void display(){
		System.out.print("stack: "); 
		List<String> list = new ArrayList(stack);
		Collections.reverse(list);
		for (String bd: list){
			System.out.print(bd + " "); 
		}
	}

	protected Deque<String> getStack() {
		return stack;
	}

	protected void setStack(Deque<String> stack) {
		// cloning
		stack.forEach(element->this.stack.add(element));
	}


}
