package com.engineeringwithramaa.inmemorydatagrid.cachestore;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.engineeringwithramaa.inmemorydatagrid.controller.OrderController;
import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.tangosol.net.cache.CacheStore;
import com.tangosol.util.Base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderCacheStore extends Base implements CacheStore {
	
	private EntityManager entityManager;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderController.class);
	
	public OrderCacheStore() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("order");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	@Override
	public Object load(Object key) {
		LOGGER.info("Order Cache Store - Checking if Value exists in Database for Key  " + key);
		Object order = null;
		try {
			//order = entityManager.find(Order.class, key);
			order = entityManager.createNamedQuery("Order.findById", Order.class)
					.setParameter("id", key)
					.getSingleResult();
		} catch (Exception exception) {
			LOGGER.info("Value not exists in Database for key " + key);
			exception.getStackTrace();
		}
		return order;
	}

	@Override
	public void store(Object key, Object value) {
		LOGGER.info("Order Cache Store -> store() - Key " + key + " Value " + value.toString());
		try {
			entityManager.getTransaction().begin();
			if(load(key) != null) {
				LOGGER.info("Order Cache Store - ASYNCHRONOUSLY UPDATING - " + value.toString());
				entityManager.merge(value);
				entityManager.flush();
			}else {
				LOGGER.info("Order Cache Store - ASYNCHRONOUSLY SAVING - " + value.toString());
				entityManager.persist(value);
			}
			entityManager.getTransaction().commit();
		} catch (Exception exception) {
			LOGGER.info("Order Cache Store -> ASYNCHRONOUSLY SAVING FAILED -> Exception - " + exception.getMessage());
			exception.printStackTrace();
		}		
	}

	@Override
	public void erase(Object key) {
		LOGGER.info("Order Cache Store -> erase() -> Key " + key);
		try {
			entityManager.getTransaction().begin();
			Order order = entityManager.find(Order.class, key);
			entityManager.remove(order);
			LOGGER.info("Order Cache Store - ASYNCHRONOUSLY DELETING - " + order.toString());
			entityManager.getTransaction().commit();
		} catch (Exception exception) {
			LOGGER.info("Order Cache Store -> ASYNCHRONOUSLY DELETING FAILED -> Exception - " + exception.getMessage());
			exception.getStackTrace();
		}
		
	}

}
