package me.sbahr.mc_calculator;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.sbahr.mc_calculator.command.CalculatorCommand;
import me.sbahr.mc_calculator.listener.CalculatorMenu;

public class Calculator extends JavaPlugin {

	@Override
	public void onEnable(){
		
		// register command
		registerCommand(new CalculatorCommand(), "calculator");
		
		new CalculatorMenu.Controller(this);
	}
	
	@Override
	public void onDisable(){
		
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
