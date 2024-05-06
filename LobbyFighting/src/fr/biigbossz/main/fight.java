package fr.biigbossz.main;


import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class fight implements CommandExecutor {
	
private main main;
	
	
	public fight(main main2) {
	this.main = main2;
}

	@Override
	public boolean onCommand(CommandSender sender, Command cmds, String label, String[] args) {
		
		
		Player p = (Player) sender;

			if (args.length == 0) {
				
Inventory drone = Bukkit.createInventory(null, 9, "§4§lLobby Fighting");
				
				ItemStack skull4 = new ItemStack(Material.SKULL_ITEM, 1);///									CAT
		    	SkullMeta skullMeta4 = (SkullMeta) skull4.getItemMeta();
		    	skull4.setDurability((short) 3);
		    	skullMeta4.setDisplayName("§cAbout");
		    	skullMeta4.setLore(Arrays.asList("","§7Author: §cBiiGBosSZ", "§7Version: §c1.1"));
		    	skullMeta4.setOwner("BiiGBosSZ");
		    	
		    	skull4.setItemMeta(skullMeta4);
		    	
		    	
		    	ItemStack battery = new ItemStack(Material.SKULL_ITEM, 1);///										DOG
		    	SkullMeta skullMeta5 = (SkullMeta) battery.getItemMeta();
		    	battery.setDurability((short) 3);
		    	skullMeta5.setDisplayName("§eAll my plugins");
		    	skullMeta5.setLore(Arrays.asList("","§7List of all my plugins available on spigot !"));
		    	skullMeta5.setOwner("CommandBlock");
		    	
		    	battery.setItemMeta(skullMeta5);
		    	
		    	
		    	ItemStack VILLAGER = new ItemStack(Material.SKULL_ITEM, 1);
		    	SkullMeta v = (SkullMeta) battery.getItemMeta();
		    	VILLAGER.setDurability((short) 3);
		    	v.setDisplayName("§4Reload plugin");
		    	v.setLore(Arrays.asList("","§7Reload this plugin !"));
		    	v.setOwner("CaoBoyy");
		    	
		    	VILLAGER.setItemMeta(v);
		    	
		    	
		    	
				drone.setItem(0, skull4);
				drone.setItem(4, battery);
				drone.setItem(8, VILLAGER);
				
				
				p.openInventory(drone);
				
			
			return true;
			
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if(p.hasPermission("lf.admin")) {
					
					
					
					main.reloadConfig();
					main.saveConfig();
					p.sendMessage(main.getConfig().getString("messages.reload").replace("&", "§"));
					p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 5);
					}
					if(!p.hasPermission("lf.admin")) {
						p.sendMessage(main.getConfig().getString("messages.noperm").replace("&", "§"));
					}
			}
			if (args[0].equalsIgnoreCase("about")) {
				p.sendMessage("§c§m        §4§l Lobby Fighting §c§m        ");
				p.sendMessage("");
				p.sendMessage("§8Author: §cBiiGBosSZ");
				p.sendMessage("§8Version: §c1.0");
				p.sendMessage("");
				p.sendMessage("§c§m                                               ");
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 5, 5);
				
				
			}
					
			
			


	
		return false;
	}
	
}