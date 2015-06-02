package com.ikolosov.spring.tx.database.service;

import com.ikolosov.spring.tx.database.model.IContainer;
import org.aspectj.lang.annotation.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * @author ikolosov
 */
public interface IContainerService {

	boolean storeContainer(IContainer container);

	boolean transferData(IContainer from,
						 IContainer to,
						 BigDecimal value);

	IContainer getContainer(int id);

	@Target({
			ElementType.METHOD,
			ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Transactional(
			propagation = Propagation.REQUIRES_NEW,
			isolation = Isolation.SERIALIZABLE)
	@interface ContainerTx {
	}

	@Aspect
	class ContainerServiceAspect {

		@Pointcut("execution(* com.ikolosov.spring.tx.database.service.IContainerService.transferData(..))")
		private void dataTransfer() {
		}

		@Before("dataTransfer()")
		private void beforeAdvice() {
			System.out.println("Data transfer started...");
		}

		@AfterReturning(
				pointcut = "dataTransfer()",
				returning = "transferred")
		public void afterReturningAdvice(boolean transferred) {
			System.out.println("Data transfer " + (transferred ? "" : "was not ") + "completed");
		}

		@AfterThrowing(
				pointcut = "dataTransfer()",
				throwing = "e")
		private void afterThrowingAdvice(RuntimeException e) {
			System.out.println("Data transfer was completed with exception "
					+ "\nTransaction rollback should occur - therefore no changes since previous step are expected...");
		}
	}
}
