package me.sbahr.mc_calculator.manager;

import java.util.UUID;

public class CalculatorCache {
	
	/** The owner of the this cache */
	private final UUID owner;
	/** The left input */
	private String inputLeft;
	/** The right input */
	private String inputRight;
	/** The operation to do with the inputs */
	private Operation operation;
	
	public CalculatorCache(UUID owner){
		this.owner = owner;
		this.inputLeft = "";
		this.inputRight = "";
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

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public double calculate(){
		
		int left = 0;
		int right = 0;
		
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
					return left + right;
				case SUBTRACT:
					return left - right;
				case MULTIPLY:
					return left * right;
				case DIVIDE:
					return left / right;
			}
		}
		
		return 0;
	}
}
