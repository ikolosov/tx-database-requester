package com.ikolosov.spring.tx.database;

import com.ikolosov.spring.tx.database.model.Container;
import com.ikolosov.spring.tx.database.model.IContainer;
import com.ikolosov.spring.tx.database.service.IContainerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

/**
 * @author ikolosov
 */
public class AtWork {

	public static final IContainer containerOne = new Container(1, "primary storage", new BigDecimal(20));
	public static final IContainer containerTwo = new Container(2, "secondary storage", new BigDecimal(45));

	public static void main(String[] args) {
		// [] app context init
		ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
		IContainerService service = (IContainerService) context.getBean("containerService");
		// [] step #1: two containers created
		System.out.println("\nstep #1: containers creation -----");
		service.storeContainer(containerOne);
		service.storeContainer(containerTwo);
		printStatus(service);
		// [] step #2: data transfer between containers (success case, transaction commit should take place)
		System.out.println("\nstep #2: success case data transfer (with tx commit) -----");
		service.transferData(
				containerOne,
				containerTwo,
				new BigDecimal(10));
		printStatus(service);
		// [] step #3: data transfer between containers (failure case, transaction rollback should take place)
		System.out.println("\nstep #3: failure case data transfer (with expected tx rollback) -----");
		try {
			service.transferData(
					containerOne,
					new Container(3, "non-existent container", new BigDecimal(0)),
					new BigDecimal(10));
		} catch (Exception e) {
			System.out.println("Exception was caught:"
					+ "\n\tClass: " + e.getClass().getSimpleName()
					+ "\n\tMessage: " + e.getMessage() + "\nTransaction rollback will occur\n");
		}
		printStatus(service);
	}

	private static void printStatus(IContainerService service) {
		service.getContainer(containerOne.getId()).printStatus();
		service.getContainer(containerTwo.getId()).printStatus();
	}
}
