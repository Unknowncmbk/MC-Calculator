# MC-Calculator
This is a project that was completed as a trial project for GodComplex.

## Overview
The overview was simple, build a calculator. No other requirements were given.

I opted for a inventory GUI calculator as it's more useful to see the calculator and operate with the results as items that are clickable, instead of typing commands.

## Design
I designed a simple two number and an operation calculator, with the operations being ADD, SUBTRACT, MULTIPLY, DIVIDE. This was due to the limited time constraints and getting a viable, tested, working product as opposed to handling infinite computations all at once.

I prioritized communication and documentation in the project in order to make sure others can follow my line of thought.

You can start with the main class object Calculator.

## Improvements
To handle multiple or infinite number inputs at a time, this project can be extended to using a Queue or a Stack implementation in the CalculationCache object. This would require splitting of the stack for operations in order (multiple/divide before add/subtract) and using a recursive builder to build the result.

More buttons can be added to the menu down the road as long as they are implementd in CalculatorMenu.