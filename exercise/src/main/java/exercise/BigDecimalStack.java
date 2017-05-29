package exercise;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class BigDecimalStack extends BaseStack{
	private static int DISPLAY_SCALE = ConfigurationVariables.getNumber("BIGDECIMAL.displayScale");
	
	@Override
	public void display(){
		System.out.print("stack: ");
		List<String> list = new ArrayList(getStack());
		Collections.reverse(list);
		for (String stringElement: list){
			BigDecimal bd = new BigDecimal(stringElement).setScale(DISPLAY_SCALE, RoundingMode.FLOOR).stripTrailingZeros();
			System.out.print(bd.toPlainString() + " ");
		}
	}
}
