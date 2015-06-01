package com.ikolosov.spring.tx.database.model;

import java.math.BigDecimal;

/**
 * @author ikolosov
 */
public class Container implements IContainer {

	private int id;
	private String description;
	private BigDecimal value;

	public Container(int id, String description, BigDecimal value) {
		this.id = id;
		this.description = description;
		this.value = value;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public BigDecimal getValue() {
		return value;
	}

	@Override
	public void decreaseValue(BigDecimal amount) {
		value = value.subtract(amount);
	}

	@Override
	public void increaseValue(BigDecimal amount) {
		value = value.add(amount);
	}

	@Override
	public void printStatus() {
		System.out.println("Container #" + id + " (" + description + ") current value is: " + value);
	}
}
