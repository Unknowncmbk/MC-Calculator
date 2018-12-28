package me.sbahr.mc_calculator.manager;

import java.util.UUID;

public class CalculatorCache {
	
	/** The owner of the this cache */
	private final UUID owner;
	/** The left input */
	private String inputLeft;
	/** The right input */
	private String inputRight;
	
	public CalculatorCache(UUID owner){
		this.owner = owner;
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
}
