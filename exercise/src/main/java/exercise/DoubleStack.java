package exercise;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoubleStack extends BaseStack {
	@Override
	public void display(){
		System.out.print("stack: ");
		List<String> list = new ArrayList(getStack());
		Collections.reverse(list);
		for (String stringElement: list){
			Double d = new Double(stringElement);
			// Not using the efficient BigDecimal class as formatter, just for a change.
			// 
			DecimalFormat df = new DecimalFormat("0.##");
			System.out.print(df.format(d) + " ");
		}
	}

}
