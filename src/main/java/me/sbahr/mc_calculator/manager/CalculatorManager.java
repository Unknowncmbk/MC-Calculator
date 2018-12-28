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

	/**
	 * Private constructor because singletons cannot be initialized.
	 */
	private CalculatorManager() {
		this.uuidToCache = new HashMap<>();
	}

	/**
	 * Get the instance of this singleton manager.
	 * 
	 * @return The instance of the singleton manager.
	 */
	public static CalculatorManager getInstance() {
		if (instance == null) {
			instance = new CalculatorManager();
		}

		return instance;
	}

	/**
	 * Get the player's cache for their calculator, if one exists.
	 * 
	 * @param uuid - the uuid of the player to lookup
	 * 
	 * @return The player's cache for their calculator, if one exists.
	 */
	public Optional<CalculatorCache> getPlayerCache(UUID uuid) {
		if (uuidToCache.containsKey(uuid)) {
			CalculatorCache cache = uuidToCache.get(uuid);
			return Optional.of(cache);
		}

		return Optional.empty();
	}

	/**
	 * Add a new player to this manager to keep track of their calculator.
	 * 
	 * @param uuid - the uuid of the user to add
	 * 
	 * @return {@code true} if the user was added to this manager, {@code false}
	 *         otherwise.
	 */
	public boolean addPlayerCache(UUID uuid) {
		if (!uuidToCache.containsKey(uuid)) {
			uuidToCache.put(uuid, new CalculatorCache(uuid));
			return true;
		}

		return false;
	}

	/**
	 * Removes the player's cache from this manager.
	 * 
	 * @param uuid - the uuid of the user to remove
	 * 
	 * @return The removed calculator cache, if one was found.
	 */
	public Optional<CalculatorCache> removePlayerCache(UUID uuid) {
		if (uuidToCache.containsKey(uuid)) {
			CalculatorCache cache = uuidToCache.remove(uuid);
			return Optional.of(cache);
		}

		return Optional.empty();
	}
	
	/**
	 * Clears all the caches in this manager.
	 */
	public void clearAllCaches(){
		uuidToCache.clear();
	}
}
