package me.sbahr.mc_calculator.listener;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.plugin.Plugin;

public class CalculatorListener implements Listener {
	
	/** The owning plugin */
	private Plugin plugin;
	
	public CalculatorListener(Plugin plugin){
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent event){
		
		HumanEntity he = event.getWhoClicked();
		if (he instanceof Player){
			
			Player p = (Player) he;
		}
		
	}

}
