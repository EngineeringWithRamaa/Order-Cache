package com.engineeringwithramaa.inmemorydatagrid.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.engineeringwithramaa.inmemorydatagrid.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private OrderService orderService;
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderController.class);
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public Collection<Order> getOrders() {
		LOGGER.info("Order Controller -> getOrders() ");
		return orderService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable String id) {
		LOGGER.info("Order Controller -> getOrderById() -> id " + id);
		return ResponseEntity.ok(orderService.find(id));
	}

	@PostMapping
	public void createOrder(@RequestBody Order order) {
		LOGGER.info("Order Controller -> createOrder() -> order " + order.toString());
		orderService.save(new Order(order.getDescription()));
	}

	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable String id) {
		LOGGER.info("Order Controller -> deleteOrder() -> id " + id);
		orderService.removeById(id);
	}

	
	@PutMapping("/{id}")
	public void updateOrder(@PathVariable String id, @RequestBody Order order) {
		LOGGER.info("Order Controller -> updateOrder() -> order " + order.toString());
		orderService.update(id, order);
	}
}