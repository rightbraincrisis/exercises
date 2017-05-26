package exercise;

abstract class AbstractFactory {
	
    // Returns a concrete factory object that is an instance of the
    // concrete factory class appropriate for the given DataType.
    static AbstractFactory getFactory(DataType dataType) {
        AbstractFactory factory = null;
        switch (dataType) {
            case BIGDECIMAL:
                factory = new BigDecimalToolkit();
                break;
            case DOUBLE:
                factory = new DoubleToolkit();
                break;
        }
        return factory;
    }

    public abstract BaseStack createStack();

    public abstract BaseMathProcessor createMathProcessor();

}
