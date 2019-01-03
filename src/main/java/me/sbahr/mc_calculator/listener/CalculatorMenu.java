package me.sbahr.mc_calculator.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.sbahr.mc_calculator.manager.CalculatorCache;
import me.sbahr.mc_calculator.manager.CalculatorManager;
import me.sbahr.mc_calculator.manager.Operation;
import me.sbahr.mc_calculator.util.ItemStackUtil;

public class CalculatorMenu implements Listener {

	/** The title of the menu */
	private static final String TITLE = "Calculator";
	/** The owning plugin */
	private Plugin plugin;

	/**
	 * Create a new CalculatorMenu.
	 * <p>
	 * This handles the click logic of the buttons of the calculator.
	 * </p>
	 * 
	 * @param plugin - the owning plugin
	 */
	public CalculatorMenu(Plugin plugin) {
		System.out.println("Registering CalculatorMenu handler...");
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Opens the menu for the given player.
	 * 
	 * @param player - the player requesting the calculator menu
	 */
	public void openMenu(Player player) {

		// grab their cached calculator
		CalculatorCache cache = CalculatorManager.getInstance().getPlayerCache(player.getUniqueId()).orElse(null);

		if (cache != null) {
			Inventory gui = constructInventory(player, cache);
			player.openInventory(gui);
		}
	}

	/**
	 * Listens in on inventory click events.
	 * <p>
	 * This method sure that the inventory that is being clicked is related to
	 * this menu.
	 * </p>
	 * 
	 * @param event - the event
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		// grab event variables
		Inventory inven = event.getClickedInventory();
		HumanEntity he = event.getWhoClicked();

		if (he instanceof Player) {
			Player player = (Player) he;

			// if this menu
			if (inven != null && inven.getTitle() != null && inven.getTitle().equalsIgnoreCase(TITLE)) {
				event.setCancelled(true);

				// handle valid clicks
				if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {

					// make sure they are clicking something
					if (event.getCurrentItem() != null) {

						// make sure it's in the top slot inventory
						if (event.getRawSlot() < inven.getSize()) {

							// handle the clicking of a button
							boolean close = handleCalculatorClick(event, player);

							if (close) {
								player.closeInventory();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Handles the clicking of the calculator buttons.
	 * 
	 * @param event - the inventory click event
	 * @param player - the player clicking
	 * 
	 * @return {@code true} if we handled the click and should close the menu,
	 *         {@code false} otherwise.
	 */
	private boolean handleCalculatorClick(InventoryClickEvent event, Player player) {

		// grab the calc cache for the player
		CalculatorCache cache = CalculatorManager.getInstance().getPlayerCache(player.getUniqueId()).orElse(null);

		if (cache == null) {
			player.sendMessage(ChatColor.RED + "Unable to locate player calculator cache!");
			return true;
		}

		// grab raw slot so we know the button
		int rawSlot = event.getRawSlot();

		switch (rawSlot) {
			case 0:
				// handle clear button
				
				cache.clearCache();
				break;
			case 10:
			case 11:
			case 12:
			case 19:
			case 20:
			case 21:
			case 28:
			case 29:
			case 30:
			case 37:
				// handle 0-9 buttosn
				
				// grab the digit pressed
				int digit = event.getCurrentItem().getAmount();

				if (rawSlot == 37) {
					digit = 0;
				}

				// if operation is not specified, add to left input
				if (cache.getOperation() == null) {
					cache.setInputLeft(cache.getInputLeft() + digit);

					player.sendMessage(ChatColor.GRAY + cache.getInputLeft());
				}
				else {
					cache.setInputRight(cache.getInputRight() + digit);

					player.sendMessage(ChatColor.GRAY + cache.getInputRight());
				}
				break;
			case 14:
			case 23:
			case 32:
			case 41:
				// handle operations buttons

				if (cache.getInputLeft().isEmpty()) {
					player.sendMessage(ChatColor.RED + "Please input a number before using operations!");
					return false;
				}

				player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + cache.getInputLeft());

				if (rawSlot == 14) {
					cache.setOperation(Operation.DIVIDE);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "DIVIDED BY");
				}
				else if (rawSlot == 23) {
					cache.setOperation(Operation.MULTIPLY);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "TIMES");
				}
				else if (rawSlot == 32) {
					cache.setOperation(Operation.SUBTRACT);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "MINUS");
				}
				else if (rawSlot == 41) {
					cache.setOperation(Operation.ADD);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "PLUS");
				}
				break;
			case 39:
				// handle equal button

				if (!cache.getInputRight().isEmpty()) {
					player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + cache.getInputRight());
				}

				player.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "Equals: " + ChatColor.WHITE + cache.calculate());

				// reopen menu
				openMenu(player);
				break;
		}

		return false;
	}

	/**
	 * Creates the inventory gui representation for the player.
	 * 
	 * @param player - the player requesting the gui
	 * @param cache - the calculator cache for this player
	 * 
	 * @return The built inventory gui for the player.
	 */
	private Inventory constructInventory(Player player, CalculatorCache cache) {

		Inventory gui = Bukkit.getServer().createInventory(null, 9 * 6, TITLE);

		// clear button
		gui.setItem(0, ItemStackUtil.createItemStack(new ItemStack(Material.BARRIER, 1), ChatColor.RED + "Clear", null));

		// digits 7 - 9
		gui.setItem(10, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 7), ChatColor.GRAY + "7", null));
		gui.setItem(11, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 8), ChatColor.GRAY + "8", null));
		gui.setItem(12, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 9), ChatColor.GRAY + "9", null));

		// digits 4-6
		gui.setItem(19, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 4), ChatColor.GRAY + "4", null));
		gui.setItem(20, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 5), ChatColor.GRAY + "5", null));
		gui.setItem(21, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 6), ChatColor.GRAY + "6", null));

		// digits 1-3
		gui.setItem(28, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 1), ChatColor.GRAY + "1", null));
		gui.setItem(29, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 2), ChatColor.GRAY + "2", null));
		gui.setItem(30, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 3), ChatColor.GRAY + "3", null));

		// digit 0
		gui.setItem(37, ItemStackUtil.createItemStack(new ItemStack(Material.BUCKET, 1), ChatColor.GRAY + "0", null));

		// equal button
		gui.setItem(39, ItemStackUtil.createItemStack(new ItemStack(Material.ANVIL, 1), ChatColor.GREEN + "Equals", null));

		// divide, multiply, subtract, add buttons
		gui.setItem(14, ItemStackUtil.createItemStack(new ItemStack(Material.BLAZE_ROD, 1), ChatColor.YELLOW + "Divide", null));
		gui.setItem(23, ItemStackUtil.createItemStack(new ItemStack(Material.END_CRYSTAL, 1), ChatColor.YELLOW + "Multiply", null));
		gui.setItem(32, ItemStackUtil.createItemStack(new ItemStack(Material.IRON_HORSE_ARMOR, 1), ChatColor.YELLOW + "Subtract", null));
		gui.setItem(41, ItemStackUtil.createItemStack(new ItemStack(Material.ARMOR_STAND, 1), ChatColor.YELLOW + "Add", null));

		String resultName = ChatColor.GREEN + "Result";
		List<String> resultLore = new ArrayList<>();

		// if "EQUALS" was pushed, there is a final operation line
		if (!cache.getFinalOperation().isEmpty()) {
			resultName = "" + ChatColor.GREEN + ChatColor.BOLD + cache.getFinalAnswer();
			resultLore.add(ChatColor.GRAY + cache.getFinalOperation());
		}

		// result button
		gui.setItem(34, ItemStackUtil.createItemStack(new ItemStack(Material.CRAFTING_TABLE, 1), resultName, resultLore));

		// fill reset in with glass
		for (int i = 0; i < gui.getSize(); i++) {
			ItemStack is = gui.getItem(i);
			if (is == null) {
				gui.setItem(i, ItemStackUtil.createItemStack(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1), " ", null));
			}
		}

		return gui;
	}

	public static class Controller {

		/** The bound menu */
		private static CalculatorMenu menu;

		/**
		 * Create a new controller for static access to this menu.
		 * 
		 * @param plugin - the owning plugin
		 */
		public Controller(Plugin plugin) {
			menu = new CalculatorMenu(plugin);
		}

		/**
		 * Open the calculator menu for the given player.
		 * 
		 * @param player - the player requesting the menu
		 */
		public static void openMenu(Player player) {
			if (menu != null) {
				menu.openMenu(player);
			}
		}
	}
}
