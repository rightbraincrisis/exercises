package exercise;

public class BigDecimalToolkit extends AbstractFactory {

	@Override
	public BaseStack createStack() {
		return new BigDecimalStack();
	}

	@Override
	public BaseMathProcessor createMathProcessor() {
		return new BigDecimalMathProcessor();
	}

}
