package com.engineeringwithramaa.inmemorydatagrid.repository;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.oracle.coherence.spring.data.config.CoherenceMap;
import com.oracle.coherence.spring.data.repository.CoherenceRepository;

@CoherenceMap("Order")
public interface SpringDataOrderRepository extends CoherenceRepository<Order, String> {
}