package exercise;

public class DoubleToolkit extends AbstractFactory {

	@Override
	public BaseStack createStack() {
		return new DoubleStack();
	}

	@Override
	public BaseMathProcessor createMathProcessor() {
		return new DoubleMathProcessor();
	}

}
