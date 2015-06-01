package com.ikolosov.spring.tx.database.model;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;

/**
 * @author ikolosov
 */
public interface IContainer {

	int getId();

	String getDescription();

	BigDecimal getValue();

	void decreaseValue(BigDecimal value);

	void increaseValue(BigDecimal value);

	void printStatus();

	static RowMapper<IContainer> createRowMapper() {
		return (rs, rowNum) -> new Container(
				rs.getInt("ID"),
				rs.getString("DESCR"),
				rs.getBigDecimal("VAL")
		);
	}
}
