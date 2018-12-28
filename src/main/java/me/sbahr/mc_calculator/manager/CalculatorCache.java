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

	/**
	 * Create a new CalculatorCache.
	 * <p>
	 * This is used for a cache to compute what a player is pressing on the
	 * calculator menu.
	 * </p>
	 * 
	 * @param owner - the owner of the cache
	 */
	public CalculatorCache(UUID owner) {
		this.owner = owner;
		this.inputLeft = "";
		this.inputRight = "";
		this.finalAnswer = 0;
		this.finalOperation = "";
	}

	/**
	 * Calculate the left/right inputs using the operation and set a result in
	 * {@link #getFinalAnswer()} as well as mutating
	 * {@link #getFinalOperation()}.
	 * 
	 * @return The result of the left/right inputs.
	 */
	public double calculate() {

		int left = 0;
		int right = 0;
		double answer = 0;

		// convert left input to int
		if (this.inputLeft != null) {
			try {
				left = Integer.parseInt(this.inputLeft);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// convert right input to int
		if (this.inputRight != null) {
			try {
				right = Integer.parseInt(this.inputRight);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (this.operation != null) {

			switch (this.operation) {
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
					if (right == 0) {
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

	/**
	 * Clears the calculator cache.
	 */
	public void clearCache() {
		this.inputLeft = "";
		this.inputRight = "";
		this.finalAnswer = 0;
		this.finalOperation = "";
	}

	/**
	 * Get the uuid of the owner of this cache.
	 * 
	 * @return The uuid of the owner of this cache.
	 */
	public final UUID getOwner() {
		return owner;
	}

	/**
	 * The left part of the input string. This represents the first number or
	 * digit the player is attempting to calculate with.
	 * 
	 * @return The left part of the input string.
	 */
	public String getInputLeft() {
		return inputLeft;
	}

	/**
	 * Set the value for the new left input.
	 * 
	 * @param inputLeft - the new left input
	 */
	public void setInputLeft(String inputLeft) {
		this.inputLeft = inputLeft;
	}

	/**
	 * The right part of the input string. This represents the second/final
	 * number or digit the player is attempting to calculate with.
	 * 
	 * @return The right part of the input string.
	 */
	public String getInputRight() {
		return inputRight;
	}

	/**
	 * Set the value for the new right input.
	 * 
	 * @param inputRight - the new right input
	 */
	public void setInputRight(String inputRight) {
		this.inputRight = inputRight;
	}

	/**
	 * Get the final cached answer of the computation.
	 * 
	 * @return The final answer of the calculator computation.
	 */
	public double getFinalAnswer() {
		return finalAnswer;
	}

	/**
	 * Set the final answer to this cache.
	 * 
	 * @param finalAnswer - the new final answer
	 */
	protected void setFinalAnswer(double finalAnswer) {
		this.finalAnswer = finalAnswer;
	}

	/**
	 * Get the final operation string for this cache.
	 * <p>
	 * For example, this is "5 + 10"
	 * </p>
	 * 
	 * @return The final operation as a string to compute.
	 */
	public String getFinalOperation() {
		return finalOperation;
	}

	/**
	 * Set the final operation string.
	 * <p>
	 * For example, this is "5 + 10"
	 * </p>
	 * 
	 * @param finalOperation - the new final operation string
	 */
	protected void setFinalOperation(String finalOperation) {
		this.finalOperation = finalOperation;
	}

	/**
	 * Get the math operation to do on the left/right inputs.
	 * 
	 * @return The math operation to compute on the string inputs.
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Set the math operations to do on the left/right inputs.
	 * 
	 * @param operation - the new operation
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
