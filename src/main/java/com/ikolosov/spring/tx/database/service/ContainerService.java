package com.ikolosov.spring.tx.database.service;

import com.ikolosov.spring.tx.database.dao.IContainerDao;
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

	private final IContainerDao<IContainer> containerDao;

	public ContainerService(IContainerDao<IContainer> containerDao) {
		this.containerDao = containerDao;
	}

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false)
	public void storeContainer(IContainer container) {
		containerDao.insert(container);
	}

	// todo: with/without tx support comparison
	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false)
	public void transferData(IContainer from,
							 IContainer to,
							 BigDecimal value) {
		from.setValue(from.getValue().subtract(value));
		containerDao.update(from);
		to.setValue(to.getValue().add(value));
		containerDao.update(to);
	}

	@Override
	public IContainer getContainer(int id) {
		return containerDao.select(id);
	}
}
