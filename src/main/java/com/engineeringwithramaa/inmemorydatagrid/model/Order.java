package com.engineeringwithramaa.inmemorydatagrid.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.io.pof.annotation.Portable;


@Entity
@Table(name="ORDER_TABLE")
@NamedQueries({
	@NamedQuery(name = "Order.findById",
			query = "SELECT o FROM Order o WHERE o.id = :id")})
public class Order implements Serializable, PortableObject {


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

	@Override
	public void readExternal(PofReader pofReader) throws IOException {
		id = pofReader.readString(0);
		createdAt = pofReader.readLong(1);
		description = pofReader.readString(2);
		completed = pofReader.readBoolean(3); 


	}

	@Override
	public void writeExternal(PofWriter pofWriter) throws IOException {
		pofWriter.writeString(0, id);
		pofWriter.writeLong(1, createdAt);
		pofWriter.writeString(2, description);
		pofWriter.writeBoolean(3, completed);

	}
}