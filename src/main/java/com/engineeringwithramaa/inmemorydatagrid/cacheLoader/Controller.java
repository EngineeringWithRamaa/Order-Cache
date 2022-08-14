package com.engineeringwithramaa.inmemorydatagrid.cacheLoader;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;

@RestController
@RequestMapping("/api/loader")
public class Controller {

	private CacheLoaderService loaderService;
	private static final Logger LOGGER=LoggerFactory.getLogger(Controller.class);
	
	private NamedCache c = CacheFactory.getCache("OrderCache");
	
	
	public Controller(CacheLoaderService loaderService) {
		this.loaderService = loaderService;
	}

	@GetMapping("/init")
	public String preLoadCache() {
		LOGGER.info("Order Controller -> getOrders() ");
		return loaderService.bulkLoader(c);
	}
	
	@GetMapping("/members")
	public void getStorageEnabledMembers() {
		LOGGER.info("Order Controller -> getStorageEnabledMembers() ");
		Set<Member> storageMembers = loaderService.getStorageMembers(c);
		LOGGER.info("Storage Enabled Members are " + storageMembers.toString());
		
	}
	

}
