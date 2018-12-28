package me.sbahr.mc_calculator.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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

	public CalculatorMenu(Plugin plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	private Inventory constructInventory(Player player) {

		Inventory gui = Bukkit.getServer().createInventory(null, 9 * 6, TITLE);

		// clear button
		gui.setItem(0, ItemStackUtil.createItemStack(new ItemStack(Material.BARRIER, 1), "CLEAR", null));

		// digits 7 - 9
		gui.setItem(10, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 7), "7", null));
		gui.setItem(11, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 8), "8", null));
		gui.setItem(12, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 9), "9", null));

		// digits 4-6
		gui.setItem(19, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 4), "4", null));
		gui.setItem(20, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 5), "5", null));
		gui.setItem(21, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 6), "6", null));

		// digits 1-3
		gui.setItem(28, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 1), "1", null));
		gui.setItem(29, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 2), "2", null));
		gui.setItem(30, ItemStackUtil.createItemStack(new ItemStack(Material.WHITE_CONCRETE, 3), "3", null));

		// digit 0
		gui.setItem(37, ItemStackUtil.createItemStack(new ItemStack(Material.BUCKET, 1), "0", null));

		// equal button
		gui.setItem(39, ItemStackUtil.createItemStack(new ItemStack(Material.ANVIL, 1), "EQUALS", null));

		// divide, multiply, subtract, add buttons
		gui.setItem(14, ItemStackUtil.createItemStack(new ItemStack(Material.BLAZE_ROD, 1), "DIVIDE", null));
		gui.setItem(23, ItemStackUtil.createItemStack(new ItemStack(Material.END_CRYSTAL, 1), "MULTIPLY", null));
		gui.setItem(32, ItemStackUtil.createItemStack(new ItemStack(Material.IRON_HORSE_ARMOR, 1), "SUBTRACT", null));
		gui.setItem(41, ItemStackUtil.createItemStack(new ItemStack(Material.ARMOR_STAND, 1), "ADD", null));

		// result button
		gui.setItem(34, ItemStackUtil.createItemStack(new ItemStack(Material.CRAFTING_TABLE, 1), "RESULT", null));

		// fill reset in with glass
		for (int i = 0; i < gui.getSize(); i++) {
			ItemStack is = gui.getItem(i);
			if (is == null) {
				gui.setItem(i, ItemStackUtil.createItemStack(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1), "", null));
			}
		}

		return gui;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		// grab event variables
		Inventory inven = event.getClickedInventory();
		HumanEntity he = event.getWhoClicked();
		
		if (he instanceof Player){
			Player player = (Player) he;
			
			// if this menu
			if (inven.getTitle() != null && inven.getTitle().equalsIgnoreCase(TITLE)) {
				event.setCancelled(true);
				
				// handle valid clicks
				if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()){
					
					// make sure they are clicking something
					if (event.getCurrentItem() != null){
						
						// make sure it's in the top slot inventory
						if (event.getRawSlot() < inven.getSize()){
							
							// handle the clicking of a button
							boolean close = handleCalculatorClick(event, player);
							
							if (close){
								player.closeInventory();
							}
						}
					}
				}
			}
		}
	}

	private boolean handleCalculatorClick(InventoryClickEvent event, Player player) {
		
		// grab the calc cache for the player
		CalculatorCache cache = CalculatorManager.getInstance().getPlayerCache(player.getUniqueId()).orElse(null);
		
		if (cache == null){
			player.sendMessage(ChatColor.RED + "Unable to locate player calculator cache!");
			return true;
		}
		
		// grab raw slot so we know the button
		int rawSlot = event.getRawSlot();
		
		switch (rawSlot){
			case 0:
				
				// handle clear button
				cache.setInputLeft("");
				cache.setInputRight("");
				cache.setOperation(null);
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
				
				// handle 0-9
				int digit = event.getCurrentItem().getAmount();
				
				if (rawSlot == 37){
					digit = 0;
				}
				
				// if operation is not specified, add to left input
				if (cache.getOperation() == null){
					cache.setInputLeft(cache.getInputLeft() + digit);
					
					player.sendMessage(ChatColor.GRAY + cache.getInputLeft());
				}
				else{
					cache.setInputRight(cache.getInputRight() + digit);
					
					player.sendMessage(ChatColor.GRAY + cache.getInputRight());
				}
				break;
			case 14:
			case 23:
			case 32:
			case 41:
				// handle operations buttons
				
				if (cache.getInputLeft().isEmpty()){
					player.sendMessage(ChatColor.RED + "Please input a number before using operations!");
					return false;
				}
				
				player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + cache.getInputLeft());
				
				if (rawSlot == 14){
					cache.setOperation(Operation.DIVIDE);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "DIVIDED BY");
				}
				else if (rawSlot == 23){
					cache.setOperation(Operation.MULTIPLY);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "TIMES");
				}
				else if (rawSlot == 32){
					cache.setOperation(Operation.SUBTRACT);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "MINUS");
				}
				else if (rawSlot == 41){
					cache.setOperation(Operation.ADD);
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "PLUS");
				}
				break;
			case 39:
				// handle equal button
				
				if (!cache.getInputRight().isEmpty()){
					player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + cache.getInputRight());
				}
				
				player.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "Equals: " + ChatColor.WHITE + cache.calculate());
				
				
				break;
			case 34:
				// handle result button
				break;
		}
		
		return false;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player p = event.getPlayer();
		
		CalculatorManager.getInstance().addPlayerCache(p.getUniqueId());

		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			
			Inventory inven = constructInventory(p);
			p.openInventory(inven);
		}, 20L);
	}

}
