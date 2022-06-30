package com.engineeringwithramaa.inmemorydatagrid.service;

import java.util.Collection;

import com.engineeringwithramaa.inmemorydatagrid.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.engineeringwithramaa.inmemorydatagrid.coherence.OrderCoherenceRepository;
import com.tangosol.util.Filter;
import com.tangosol.util.Filters;

@Service
public class OrderService {

	private static final String ORDER_NOT_FOUND_MESSAGE = "Unable to find Order with id '%s' - ";

	private final OrderCoherenceRepository orderCoherenceRepository;

	public OrderService(OrderCoherenceRepository orderCoherenceRepository) {
		this.orderCoherenceRepository = orderCoherenceRepository;
	}
	
	public Collection<Order> findAll(boolean completed) {
		final Filter<Order> filter = !completed
				? Filters.always()
				: Filters.equal(Order::getCompleted, true);
		return orderCoherenceRepository.getAllOrderedBy(filter, Order::getCreatedAt);
	}

	public Order find(String id) throws OrderNotFoundException {
		//Assert.hasText(id, "The Order Id must not be null or empty.");
		final Order order = orderCoherenceRepository.findById(id).orElseThrow(() ->
				new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, id)));
		return order;
	}

	public void save(Order order) {
		Assert.notNull(order, "The order must not be null.");
		orderCoherenceRepository.save(order);
	}

	public void removeById(String id) {
		Assert.hasText(id, "The order Id must not be null or empty.");
		orderCoherenceRepository.deleteById(id);
	}

	public void deleteCompletedOrders() {
		orderCoherenceRepository.deleteAll(Filters.equal(Order::getCompleted, true), false);
	}

	public Collection<Order> deleteCompletedOrdersAndReturnRemainingOrders() {
		this.deleteCompletedOrders();
		return this.findAll(false);
	}


	public Order update(String id, Order order) {
		Assert.hasText(id, "The Order Id must not be null or empty.");
		Assert.notNull(order, "The Order must not be null.");

		final String description = order.getDescription();
		final Boolean completed = order.getCompleted();

		try {
			return this.orderCoherenceRepository.update(id, odr -> {
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