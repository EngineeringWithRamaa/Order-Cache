package com.engineeringwithramaa.inmemorydatagrid.coherence;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.oracle.coherence.spring.data.config.CoherenceMap;
import com.oracle.coherence.spring.data.repository.CoherenceRepository;

@CoherenceMap("Order")
public interface OrderCoherenceRepository extends CoherenceRepository<Order, String> {
}