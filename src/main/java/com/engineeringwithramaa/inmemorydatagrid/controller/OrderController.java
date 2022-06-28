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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.engineeringwithramaa.inmemorydatagrid.service.OrderNotFoundException;
import com.engineeringwithramaa.inmemorydatagrid.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public Collection<Order> getOrders(@RequestParam(defaultValue = "false") boolean completed) {
		return orderService.findAll(completed);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable String id) throws OrderNotFoundException{
		return ResponseEntity.ok(orderService.find(id));
	}

	@PostMapping
	public void createOrder(@RequestBody Order order) {
		orderService.save(new Order(order.getDescription()));
	}

	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable String id) {
		orderService.removeById(id);
	}

	@DeleteMapping
	public void deleteCompletedOrders() {
		orderService.deleteCompletedOrders();
	}

	@PutMapping("/{id}")
	public Order updateOrder(@PathVariable String id, @RequestBody Order order) {
		return orderService.update(id, order);
	}
}