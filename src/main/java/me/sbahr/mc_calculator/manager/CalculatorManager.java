package me.sbahr.mc_calculator.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CalculatorManager {
	
	/** The instance of this manager */
	private static CalculatorManager instance;
	/** Maps uuid to their calc cache */
	private Map<UUID, CalculatorCache> uuidToCache;
	
	private CalculatorManager(){
		this.uuidToCache = new HashMap<>();
	}
	
	public static CalculatorManager getInstance(){
		if (instance == null){
			instance = new CalculatorManager();
		}
		
		return instance;
	}
	
	public Optional<CalculatorCache> getPlayerCache(UUID uuid){
		if (uuidToCache.containsKey(uuid)){
			CalculatorCache cache = uuidToCache.get(uuid);
			return Optional.of(cache);
		}
		
		return Optional.empty();
	}
	
	public boolean addPlayerCache(UUID uuid){
		if (!uuidToCache.containsKey(uuid)){
			uuidToCache.put(uuid, new CalculatorCache(uuid));
			return true;
		}
		
		return false;
	}

	public Optional<CalculatorCache> removePlayerCache(UUID uuid){
		if (uuidToCache.containsKey(uuid)){
			CalculatorCache cache = uuidToCache.remove(uuid);
			return Optional.of(cache);
		}
		
		return Optional.empty();
	}
}
