package com.engineeringwithramaa.inmemorydatagrid.service;

import java.util.Collection;

import com.engineeringwithramaa.inmemorydatagrid.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.engineeringwithramaa.inmemorydatagrid.coherence.SpringDataOrderRepository;
import com.tangosol.util.Filter;
import com.tangosol.util.Filters;

@Service
public class SpringDataOrderService implements OrderService{

	private static final String ORDER_NOT_FOUND_MESSAGE = "Unable to find Order with id '%s' - ";

	private final SpringDataOrderRepository orderRepository;

	public SpringDataOrderService(SpringDataOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public Collection<Order> findAll(boolean completed) {
		final Filter<Order> filter = !completed
				? Filters.always()
				: Filters.equal(Order::getCompleted, true);
		return orderRepository.getAllOrderedBy(filter, Order::getCreatedAt);
	}

	@Override
	public Order find(String id) throws OrderNotFoundException {
		//Assert.hasText(id, "The Order Id must not be null or empty.");
		final Order order = orderRepository.findById(id).orElseThrow(() ->
				new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, id)));
		return order;
	}

	@Override
	public void save(Order order) {
		Assert.notNull(order, "The order must not be null.");
		orderRepository.save(order);
	}

	@Override
	public void removeById(String id) {
		Assert.hasText(id, "The order Id must not be null or empty.");
		orderRepository.deleteById(id);
	}

	@Override
	public void deleteCompletedOrders() {
		orderRepository.deleteAll(Filters.equal(Order::getCompleted, true), false);
	}

	@Override
	public Collection<Order> deleteCompletedOrdersAndReturnRemainingOrders() {
		this.deleteCompletedOrders();
		return this.findAll(false);
	}

	@Override
	public Order update(String id, Order order) {
		Assert.hasText(id, "The Order Id must not be null or empty.");
		Assert.notNull(order, "The Order must not be null.");

		final String description = order.getDescription();
		final Boolean completed = order.getCompleted();

		try {
			return this.orderRepository.update(id, odr -> {
				if (description != null) {
					odr.setDescription(description);
				}
				if (completed != null) {
					odr.setCompleted(completed);
				}
				return odr;
			});
		}
		catch (Exception ex) {
			throw new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, id));
		}
	};
}