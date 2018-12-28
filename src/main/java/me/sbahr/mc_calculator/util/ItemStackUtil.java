package me.sbahr.mc_calculator.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtil {
	
	/**
	 * Mutate a the given itemstack with new displayName and lore if necessary.
	 * 
	 * @param is - the itemstack to change
	 * @param displayName - the new displayname for the itemstack
	 * @param lore - the new lore for the itemstack
	 * 
	 * @return The itemstack with the new name and lore if they were provided.
	 */
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
