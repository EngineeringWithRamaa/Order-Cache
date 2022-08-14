package com.engineeringwithramaa.inmemorydatagrid.cacheLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.engineeringwithramaa.inmemorydatagrid.model.Order;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.InvocationObserver;
import com.tangosol.net.InvocationService;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;
import com.tangosol.net.PartitionedService;

@Service
public class CacheLoaderService {

	private CacheLoaderRepository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheLoaderService.class);


	public CacheLoaderService(CacheLoaderRepository repository) {
		super();
		this.repository = repository;
	}

	public String bulkLoader(NamedCache cache) {

		String sCacheName = cache.getCacheName();
		Map<String, Order> buffer = new HashMap<String, Order>();

		LOGGER.info("CacheLoaderService - Inside getAllOrders");
		List<Order> orders = repository.getAllOrders();

		for (Order order : orders)
			buffer.put(order.getId(), order);

		cache.putAll(buffer);

		// Logic for demonstrating Controllable Cache
		/*
		 * ControllableCacheStore1.disable(sCacheName); cache.putAll(buffer);
		 * ControllableCacheStore1.enable(sCacheName); Order newOrder = new
		 * Order("order created to test if write-back async gets triggered");
		 * cache.put(newOrder.getId(), newOrder);
		 * 
		 */
		return "Cache pre-loaded successfully";
	}

	public Set<Member> getStorageMembers(NamedCache cache)
	{
		return ((PartitionedService) cache.getCacheService())
				.getOwnershipEnabledMembers();
	}
		
	protected Set<Member> getLoadingMembers(InvocationService serviceInv)
	{
		Set setMembers = serviceInv.getInfo().getServiceMembers();
		setMembers.remove(serviceInv.getCluster().getLocalMember());
		int cMembers = setMembers.size();
		if (cMembers == 0)
		{
			throw new IllegalStateException("No other members are running InvocationService. Is the cluster up?");
		}
		return setMembers;
	}

}
