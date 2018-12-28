package me.sbahr.mc_calculator.manager;

import java.util.UUID;

public class CalculatorCache {
	
	/** The owner of the this cache */
	private final UUID owner;
	/** The left input */
	private String inputLeft;
	/** The right input */
	private String inputRight;
	/** The calculated final answer */
	private double finalAnswer;
	/** The final operation string */
	private String finalOperation;
	/** The operation to do with the inputs */
	private Operation operation;
	
	public CalculatorCache(UUID owner){
		this.owner = owner;
		this.inputLeft = "";
		this.inputRight = "";
		this.finalAnswer = 0;
		this.finalOperation = "";
	}
	
	public final UUID getOwner(){
		return owner;
	}

	public String getInputLeft() {
		return inputLeft;
	}

	public void setInputLeft(String inputLeft) {
		this.inputLeft = inputLeft;
	}

	public String getInputRight() {
		return inputRight;
	}

	public void setInputRight(String inputRight) {
		this.inputRight = inputRight;
	}
	
	public double getFinalAnswer() {
		return finalAnswer;
	}

	public void setFinalAnswer(double finalAnswer) {
		this.finalAnswer = finalAnswer;
	}

	public String getFinalOperation() {
		return finalOperation;
	}

	public void setFinalOperation(String finalOperation) {
		this.finalOperation = finalOperation;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public double calculate(){
		
		int left = 0;
		int right = 0;
		double answer = 0;
		
		// convert left input to int
		if (this.inputLeft != null){
			try{
				left = Integer.parseInt(this.inputLeft);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		// convert right input to int
		if (this.inputRight != null){
			try{
				right = Integer.parseInt(this.inputRight);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if (this.operation != null){
			
			switch (this.operation){
				case ADD:
					setFinalOperation(left + " + " + right);
					answer = left + right;
				case SUBTRACT:
					setFinalOperation(left + " - " + right);
					answer = left - right;
				case MULTIPLY:
					setFinalOperation(left + " * " + right);
					answer = left * right;
				case DIVIDE:
					
					// cannot divide by 0
					if (right == 0){
						setFinalOperation("CANNOT DIVIDE BY ZERO");
						answer = 0;
					}
					
					setFinalOperation(left + " / " + right);
					answer = (double) left / right;
			}
		}
		
		// set the final calculated answer
		setFinalAnswer(answer);
		
		return answer;
	}
}
