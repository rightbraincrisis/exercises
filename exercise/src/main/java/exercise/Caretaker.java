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
        //System.out.println("Size: " + mementos.size());
    }

    public BaseStack getMemento() {
    	//System.out.println("Size: " + mementos.size());
    	mementos.pop();
        return mementos.peek();
    }
    
    public BaseStack getLatestMemento() {
    	//System.out.println("Size: " + mementos.size());
        return mementos.peek();
    }
    
    public int size(){
    	//System.out.println("Size: " + mementos.size());
    	return mementos.size();
    }

}
