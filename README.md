# README #

This README document the steps RPN Calculator up and running.

### First things first? ###

* [Look at the presentation](https://docs.google.com/presentation/d/1bJu6nGNOM0fr82yh2sl7KZwORb5U-czWfe0DRrgYTKg/edit?usp=sharing)

### How do I get set up? ###

* Import / build it as a standard maven project
* Run it from command line or eclipse

### Pending ###

* Writing tests
* Exception handling

@startuml

title RPNCalculator - Class Diagram


class AbstractFactory {
   +dataType
   +AbstractFactory getFactory()
}

class BigDecimalToolkit{
   +createStack()
   +createMathProcessor()
}

class DoubleToolkit{
   +createStack()
   +createMathProcessor()
}
class BaseStack{
   +stack: Deque<String>
   +push()
   +pop()
   +isCommand()
   +executeCommand()
   +display()
}
class BigDecimalStack{
   +display()
}
class DoubleStack{
   +display()
}

class Calculator{
   +process()
}

class BaseMathProcessor{
   +performBinaryOperation()
   +performUnaryOperation()
   +isValidOperand()
   +isOperatorBinary()
   +isOperatorUnary()
}

class Caretaker{
   -mementos: Deque<BaseStack>
   +pushMemento()
   +popMemento()
   +isUndoCommand()
   +isClearCommand()
}

class Main{

}
Main ..> Calculator: create
Calculator ..> AbstractFactory: create
Calculator ..> BaseStack: use
Calculator ..> BaseMathProcessor: use
AbstractFactory <|-down- BigDecimalToolkit: Inheritance
AbstractFactory <|-down- DoubleToolkit: Inheritance
BigDecimalToolkit..>BigDecimalMathProcessor: create
BigDecimalToolkit..>BigDecimalStack: create
DoubleToolkit..>DoubleMathProcessor: create
DoubleToolkit..>DoubleStack: create
Caretaker "1" *-- "many" BaseStack 

BaseStack <|-down- BigDecimalStack: Inheritance
BaseStack <|-down- DoubleStack: Inheritance
BaseMathProcessor <|-down- BigDecimalMathProcessor: Inheritance
BaseMathProcessor <|-down- DoubleMathProcessor: Inheritance


@enduml

### Who do I talk to? ###

* you know who i am :)
