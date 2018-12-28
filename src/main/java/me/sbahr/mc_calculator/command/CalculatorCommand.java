package me.sbahr.mc_calculator.command;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import me.sbahr.mc_calculator.listener.CalculatorMenu;

public class CalculatorCommand extends BukkitCommand {

	/**
	 * Create a new CalculatorCommand which is used to open the calculator.
	 */
	public CalculatorCommand() {
		super("calculator", "Opens the calculator", "/calculator", Arrays.asList("calc"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(CommandSender sender, String command, String[] args) {
		
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			// open the calculator for this player
			CalculatorMenu.Controller.openMenu(player);
		}
		
		return true;
	}
}
