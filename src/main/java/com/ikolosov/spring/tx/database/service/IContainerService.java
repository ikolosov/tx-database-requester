package com.ikolosov.spring.tx.database.service;

import com.ikolosov.spring.tx.database.model.IContainer;

import java.math.BigDecimal;

/**
 * @author ikolosov
 */
public interface IContainerService {

	boolean storeContainer(IContainer container);

	void moveValue(IContainer from,
				   IContainer to,
				   BigDecimal value);

	IContainer getContainer(int id);
}
