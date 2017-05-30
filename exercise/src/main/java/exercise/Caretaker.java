package exercise;

import java.util.Deque;
import java.util.LinkedList;

import exercise.exception.InsufficientElementsException;

public class Caretaker {
	
	private Deque<BaseStack> mementos;
	private static final String CLEAR = "clear";
	private static final String UNDO = "undo";
	//Extension possible to externalise clear & undo
	
	public Caretaker() {
		 mementos = new LinkedList<>();
	}

    public void pushMemento(BaseStack m) {
        mementos.push(m);
    }

    public BaseStack popMemento() throws InsufficientElementsException{
    	if(mementos.isEmpty()) throw new  InsufficientElementsException("No elements to pop");
    	else mementos.pop();
        return mementos.peek();
    }
    
    public BaseStack getLatestMemento() {
        return mementos.peek();
    }
    
    public int size(){
    	return mementos.size();
    }

	public void clear() {
		mementos.clear();		
	}

	public boolean isUndoCommand(String element) {		
		return  element.equals(UNDO);
	}
	
	public boolean isClearCommand(String element) {
		return  element.equals(CLEAR);
	}
}
