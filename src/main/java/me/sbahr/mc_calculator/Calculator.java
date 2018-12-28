package me.sbahr.mc_calculator;

import org.bukkit.plugin.java.JavaPlugin;

import me.sbahr.mc_calculator.listener.CalculatorMenu;

public class Calculator extends JavaPlugin {

	@Override
	public void onEnable(){
		new CalculatorMenu(this);
		new CalculatorMenu.Controller(this);
	}
	
	@Override
	public void onDisable(){
		
	}
}
