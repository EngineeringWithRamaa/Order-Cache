package com.engineeringwithramaa.inmemorydatagrid.service;

import java.util.Collection;

import com.engineeringwithramaa.inmemorydatagrid.exception.OrderNotFoundException;
import com.engineeringwithramaa.inmemorydatagrid.model.Order;

public interface OrderService {
	Collection<Order> findAll(boolean completed);
	Order find(String id) throws OrderNotFoundException;
	void save(Order Order);
	void removeById(String id);
	void deleteCompletedOrders();
	Collection<Order> deleteCompletedOrdersAndReturnRemainingOrders();
	Order update(String id, Order order);
}