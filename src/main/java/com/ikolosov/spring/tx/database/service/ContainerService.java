package com.ikolosov.spring.tx.database.service;

import com.ikolosov.spring.tx.database.dao.ContainerDao;
import com.ikolosov.spring.tx.database.model.IContainer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author ikolosov
 */
// todo: introduce custom tx annotation
@Transactional(
		propagation = Propagation.SUPPORTS,
		readOnly = true)
public class ContainerService implements IContainerService {

	private final ContainerDao containerDao;

	public ContainerService(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false,
			rollbackFor = Exception.class)
	public boolean storeContainer(IContainer container) {
		return containerDao.insert(container);
	}

	// todo: with/without tx support comparison
	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false,
			rollbackFor = Exception.class)
	public void moveValue(IContainer from,
						  IContainer to,
						  BigDecimal value) {
		from.decreaseValue(value);
		to.increaseValue(value);
		containerDao.update(from);
		containerDao.update(to);
	}

	@Override
	public IContainer getContainer(int id) {
		return containerDao.select(id);
	}
}
