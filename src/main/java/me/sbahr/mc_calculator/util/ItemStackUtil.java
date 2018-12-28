package me.sbahr.mc_calculator.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtil {
	
	public static ItemStack createItemStack(ItemStack is, String displayName, List<String> lore){
		ItemMeta im = is.getItemMeta();
		
		if (im != null){
			im.setDisplayName(displayName);
		}
		
		if (lore != null){
			im.setLore(lore);
		}
		
		is.setItemMeta(im);
		
		return is;
	}

}
