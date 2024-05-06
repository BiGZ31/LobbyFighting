package fr.biigbossz.main;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class guimenu implements Listener {
	
	
	@EventHandler
	public void onclick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		Player p = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(inv.getName().equalsIgnoreCase("§4§lLobby Fighting")) {
			event.setCancelled(true);
			
			
			
			if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§4Reload Plugin")){
				p.closeInventory();
				
				
				TextComponent message = new TextComponent("§bClick here to confirm");
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lf reload"));
				p.spigot().sendMessage(message);
				
				
				
				
					return;
		}
			
			if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§eAll my plugins")){
				
				Inventory plugins = Bukkit.createInventory(null, 9, "§6§LMy Plugins");
				
				ItemStack skull4 = new ItemStack(Material.SKULL_ITEM, 1);///									CAT
		    	SkullMeta skullMeta4 = (SkullMeta) skull4.getItemMeta();
		    	skull4.setDurability((short) 3);
		    	skullMeta4.setDisplayName("§4Lobby Fighting");
		    	skullMeta4.setLore(Arrays.asList("","§7A plugin to enable pvp at your lobby", "§7Version: §c1.0"));
		    	skullMeta4.setOwner("BiiGBosSZ");
		    	
		    	skull4.setItemMeta(skullMeta4);
		    	
		    	
		    	ItemStack battery = new ItemStack(Material.SKULL_ITEM, 1);///										DOG
		    	SkullMeta skullMeta5 = (SkullMeta) battery.getItemMeta();
		    	battery.setDurability((short) 3);
		    	skullMeta5.setDisplayName("§eAudible Chat");
		    	skullMeta5.setLore(Arrays.asList("","§7Make sounds when you talk !", "§7Version: §e1.0"));
		    	skullMeta5.setOwner("Dora_Aventureira");
		    	
		    	battery.setItemMeta(skullMeta5);
		    	
		    	
		    	
		    	
		    	
				plugins.setItem(0, skull4);
				plugins.setItem(1, battery);
				
				
				p.openInventory(plugins);
				return;
			}
		}
		if(inv.getName().equalsIgnoreCase("§6§LMy Plugins")) {
			event.setCancelled(true);
			
			if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§4Lobby Fighting")){
				p.sendMessage("§7Plugin website: §bhttps://www.spigotmc.org/resources/%E2%9A%94%EF%B8%8F-lobby-fighting-%E2%9A%94%EF%B8%8F-pvp-in-all-your-lobbies.99813");
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 5, 5);
				return;
			}
			
			if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§eAudible Chat")){
				p.sendMessage("§7Plugin website: §bhttps://www.spigotmc.org");
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CAT_AMBIENT, 5, 5);
				return;
			}
			
			
		}
	}

				

}
