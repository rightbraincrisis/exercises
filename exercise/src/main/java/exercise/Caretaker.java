package exercise;

import java.util.Deque;
import java.util.LinkedList;

public class Caretaker {
	
	private Deque<BaseStack> mementos;

	public Caretaker() {
		 mementos = new LinkedList<>();
	}

    public void addMemento(BaseStack m) {
        mementos.push(m);
    }

    public BaseStack getMemento() {
    	mementos.pop();
        return mementos.peek();
    }
    
    public BaseStack getLatestMemento() {
        return mementos.peek();
    }
    
    public int size(){
    	return mementos.size();
    }

}
