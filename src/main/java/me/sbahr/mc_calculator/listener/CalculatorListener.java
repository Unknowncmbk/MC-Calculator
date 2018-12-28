package me.sbahr.mc_calculator.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import me.sbahr.mc_calculator.manager.CalculatorManager;

public class CalculatorListener implements Listener {
	
	/** The owning plugin */
	private Plugin plugin;
	
	/**
	 * Construct a new CalculatorListener.
	 * <p>
	 * This handles some flow logic for the calculator manager and persistency.
	 * </p>
	 * 
	 * @param plugin - the owning plugin
	 */
	public CalculatorListener(Plugin plugin){
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Listens in on player join events for when they join the server.
	 * 
	 * @param event - the event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		// grab event variables
		Player p = event.getPlayer();
		
		// add their calculator cache
		CalculatorManager.getInstance().addPlayerCache(p.getUniqueId());
	}
	
	/**
	 * Listens in on player quit events for when they leave the server.
	 * 
	 * @param event - the event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		// grab event variables
		Player p = event.getPlayer();
		
		// add their calculator cache
		CalculatorManager.getInstance().removePlayerCache(p.getUniqueId());
	}
}
