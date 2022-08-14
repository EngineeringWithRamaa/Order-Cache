package com.engineeringwithramaa.inmemorydatagrid.cacheLoader;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;

@Repository
public class CacheLoaderRepository {

	private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheLoaderRepository.class);

	public CacheLoaderRepository() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("order");
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	public List<Order> getAllOrders() {
		LOGGER.info("Inside getAllOrders");
		List<Order> orders = entityManager.createQuery("from Order").getResultList();
		LOGGER.info("Returning from getAllOrders + Orders ");
		return orders;
	}  


}
