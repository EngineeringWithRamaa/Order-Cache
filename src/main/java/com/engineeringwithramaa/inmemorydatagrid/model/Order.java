package com.engineeringwithramaa.inmemorydatagrid.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Order implements Serializable {

	
	//The Order ID
	@Id
	private String id;

	//The creation time.
	private Long createdAt;
 
	//The completion status.
	private Boolean completed;

	//The order description.
	private String description;

	public Order() {
	}

	public Order(String description) {
		this.id = UUID.randomUUID().toString().substring(0, 6);
		this.createdAt = Instant.now().toEpochMilli();
		this.description = description;
		this.completed = false;
	}

	public Long getCreatedAt() {
		return createdAt;
	}
	public String getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public LocalDateTime getCreatedAtDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.systemDefault());
	}

	@Override
	public String toString() {
		return "Order{"
			+ "id=" + id
			+ ", description=" + description
			+ ", completed=" + completed
			+ '}';
		}
	}