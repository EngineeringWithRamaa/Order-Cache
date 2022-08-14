package com.engineeringwithramaa.inmemorydatagrid.serializer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofSerializer;
import com.tangosol.io.pof.PofWriter;

public class OrderSerializer implements PofSerializer {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(PofSerializer.class);

	@Override
	public void serialize(PofWriter pofWriter, Object value) throws IOException {
		Order order = (Order) value;
		
		LOGGER.info("Serializing Order - " + order.getId() + " " +  order.getCreatedAt()
			+ " " + order.getDescription() + " " + order.getCompleted());
		
		pofWriter.writeString(0, order.getId());
		pofWriter.writeLong(1, order.getCreatedAt());
		pofWriter.writeString(2, order.getDescription());
		pofWriter.writeBoolean(3, order.getCompleted());
		
		pofWriter.writeRemainder(null);
	}

	@Override
	public Object deserialize(PofReader pofReader) throws IOException {
		Order order = new Order();
		order.setId(pofReader.readString(0));
		order.setCreatedAt(pofReader.readLong(1));
		order.setDescription(pofReader.readString(2));
		order.setCompleted(pofReader.readBoolean(3)); 
		
		pofReader.readRemainder();
		
		LOGGER.info("DeSerializing Order - " + order.getId() + " " + 
			+ order.getCreatedAt() + " " + order.getDescription() + " " + order.getCompleted()); 
		 
		return order;
	}

}
