package com.engineeringwithramaa.inmemorydatagrid.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.oracle.coherence.spring.annotation.Name;
import com.oracle.coherence.spring.configuration.annotation.CoherenceMap;
import com.tangosol.net.NamedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
	@CoherenceMap
	@Name("OrderCache")
	private NamedMap<String, Order> orderCache;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderService.class);
	
	public Order find(String id) {
		LOGGER.info("Order Service -> ORDER NAMED MAP GET - id " + id);
		Assert.hasText(id, "The Order Id must not be null or empty.");
		final Order order = orderCache.get(id);
		LOGGER.info("Order Service - ORDER NAMED MAP - order -  " + order.toString());
		return order;
	}
	
	public Collection<Order> findAll() {
		LOGGER.info("Order Service -> ORDER NAMED MAP GET ALL ");
		return orderCache.values();
	}
	
	public void save(Order order) {
		LOGGER.info("Order Service -> ORDER NAMED MAP PUT - order  " + order.toString());
		Assert.notNull(order, "The order must not be null.");
		orderCache.put(order.getId(), order);
	}
	
	public void removeById(String id) {
		LOGGER.info("Order Service -> ORDER NAMED MAP REMOVE - id " + id);
		Assert.hasText(id, "The order Id must not be null or empty.");
		orderCache.remove(id);
	}

	public void update(String id, Order order) {
		LOGGER.info("Order Service -> update() -> order " + order.toString());
		Assert.hasText(id, "The Order Id must not be null or empty.");
		Assert.notNull(order, "The Order must not be null.");
		
		orderCache.merge(id, order, (v1, v2) -> v1.getCompleted() != v2.getCompleted() ? v2 : v1);
		LOGGER.info("Order Service -> ORDER NAMED MAP AFTER UPDATE - order "+ orderCache.get(id));
			
	}

}
