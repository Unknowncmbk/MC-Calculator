package me.sbahr.mc_calculator;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.sbahr.mc_calculator.command.CalculatorCommand;
import me.sbahr.mc_calculator.listener.CalculatorListener;
import me.sbahr.mc_calculator.listener.CalculatorMenu;
import me.sbahr.mc_calculator.manager.CalculatorManager;

public class Calculator extends JavaPlugin {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable(){
		
		// register command
		registerCommand(new CalculatorCommand(), "calculator");
		
		// register listeners
		new CalculatorListener(this);
		new CalculatorMenu.Controller(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDisable(){
		
		// clean up all caches
		CalculatorManager.getInstance().clearAllCaches();
	}
	
	/**
	 * Registers the command, skipping plugin.yml.
	 * 
	 * @param command - the command to register
	 * @param usage - the usage for that command
	 */
	public void registerCommand(BukkitCommand command, String usage) {
		try {
			
			// grab command maps
			final Field cmdMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			cmdMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) cmdMap.get(Bukkit.getServer());

			commandMap.register(usage, command);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
