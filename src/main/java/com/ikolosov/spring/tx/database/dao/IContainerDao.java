package com.ikolosov.spring.tx.database.dao;

import com.ikolosov.spring.tx.database.model.IContainer;

/**
 * @author ikolosov
 */
public interface IContainerDao<T extends IContainer> {

	boolean insert(T container);

	T select(int id);

	boolean update(T container);

	boolean delete(T container);
}
