package com.ikolosov.spring.tx.database.service;

import com.ikolosov.spring.tx.database.dao.IContainerDao;
import com.ikolosov.spring.tx.database.model.IContainer;

import java.math.BigDecimal;

/**
 * @author ikolosov
 */
public class ContainerService implements IContainerService {

	private final IContainerDao<IContainer> containerDao;

	private ContainerService(IContainerDao<IContainer> containerDao) {
		this.containerDao = containerDao;
	}

	@Override
	public boolean storeContainer(IContainer container) {
		return containerDao.insert(container);
	}

	@Override
	@ContainerTx
	public boolean transferData(IContainer from,
								IContainer to,
								BigDecimal value) {
		from.setValue(from.getValue().subtract(value));
		boolean subtracted = containerDao.update(from);
		to.setValue(to.getValue().add(value));
		boolean added = containerDao.update(to);
		return subtracted && added;
	}

	@Override
	public IContainer getContainer(int id) {
		return containerDao.select(id);
	}
}
