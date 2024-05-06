package fr.biigbossz.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;



public class pvp_1_10_R1 implements Listener {
	
	
	private HashMap<UUID,Long> pvpenabled = new HashMap<UUID,Long>();
	private HashMap<UUID,Long> pvpdisabling = new HashMap<UUID,Long>();
	private HashMap<UUID,Long> cooldown = new HashMap<UUID,Long>();
	private int cooldowntime = 5;
	private HashMap<UUID, ItemStack[]> inventory = new HashMap<>();
	private HashMap<String, Integer> killstreak = new HashMap<String, Integer>();
	
	/// configuration file
	private main main;
	public pvp_1_10_R1(main main) {
		this.main = main;
	}
	
	

	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onhit(EntityDamageByEntityEvent event){
		Entity player = event.getDamager();
		Entity damagereciever = event.getEntity();
		
		if (!(player.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		if (!(damagereciever.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		if (player instanceof Arrow) {																													/// BOW CHECK
			if(!pvpenabled.containsKey(damagereciever.getUniqueId())) {
			event.setCancelled(true);
			}
			return;
		}
		if(damagereciever instanceof HumanEntity) {
			///																											CHECKING THE PVP STATUS OF EACH PLAYER
			if (!pvpenabled.containsKey(damagereciever.getUniqueId())) {
				
				player.sendMessage(main.getConfig().getString("messages.targetnopvp").replace("&", "§"));
				((Player) player).playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 5, 5);
				event.setCancelled(true);
				return;
				
			}

			if (!pvpenabled.containsKey(player.getUniqueId())) {
				if ((player.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			player.sendMessage(main.getConfig().getString("messages.playernopvp").replace("&", "§"));
			   event.setCancelled(true);
			   return;
		   }
		}
			if (!pvpenabled.containsKey(damagereciever.getUniqueId())) {
				
				player.sendMessage(main.getConfig().getString("messages.targetnopvp").replace("&", "§"));
				if(main.getConfig().getString("options.sound") == "true") {////																						1.8 sound
				((Player) player).playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 5, 5);
				}
				event.setCancelled(true);
				return;
				
			}
			
			
			
			///																														DISABLING PVP
			if (pvpdisabling.containsKey(player.getUniqueId())) {
				pvpdisabling.remove(player.getUniqueId());
				player.sendMessage((main.getConfig().getString("messages.attacked").replace("&", "§")));
				
			}
			if (pvpdisabling.containsKey(damagereciever.getUniqueId())) {
				if(pvpenabled.containsKey(player.getUniqueId())) {
				pvpdisabling.remove(damagereciever.getUniqueId());
				damagereciever.sendMessage((main.getConfig().getString("messages.gotattacked").replace("&", "§")));
				damagereciever.getWorld().playSound(damagereciever.getLocation(), Sound.BLOCK_ANVIL_LAND, 5, 5);
				}
			}
			
			
			
			
			
			}																										
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)																		///INTERACTIONS
	public void onIntercatininv(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		if (!(player.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		if(it != null && it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("messages.sword").replace("&", "§"))){
			if(action == Action.RIGHT_CLICK_AIR ||  action.equals(Action.RIGHT_CLICK_BLOCK)){
				inventory.put(player.getUniqueId(), player.getInventory().getContents());
				pvpenabled.put(player.getUniqueId(), null);
				player.sendMessage(main.getConfig().getString("messages.enablingpvp").replace("&", "§"));
				if(main.getConfig().getString("options.sound") == "true") {////																						
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 5);
				}
				player.getInventory().clear();
				///GIVE ALL THE KIT
				player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
				if(main.getConfig().getString("options.fishingrod") == "true") {
					player.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1));
				}
				if(main.getConfig().getString("options.bow") == "true") {
					player.getInventory().addItem(new ItemStack(Material.BOW, 1));
					player.getInventory().setItem(34, new ItemStack(Material.ARROW, 16));
				}
				if(main.getConfig().getString("options.gapplestart") == "true") {
				player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));
				}
				if(main.getConfig().getString("options.shield") == "true") {
					player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
					}
				if(main.getConfig().getString("options.steak") == "true") {
					player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
					}
				
				player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
				player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
				
				///disable item (try to find nice skull) LATER 																					 TRY TO FIND SKULL HERE
				ItemStack l = new ItemStack(Material.REDSTONE_BLOCK, 1);
				ItemMeta leave = l.getItemMeta();
				leave.setDisplayName(main.getConfig().getString("messages.redstoneblock").replace("&", "§"));
				leave.setLore(Arrays.asList("", (main.getConfig().getString("messages.redstonelore").replace("&", "§"))));
				l.setItemMeta(leave);
				player.getInventory().setItem(8, l);
				
				
				
			}
		}
		///															REDSTONEBLOCK DISABLER
		if(it != null && it.getType() == Material.REDSTONE_BLOCK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("messages.redstoneblock").replace("&", "§"))){
			if(action == Action.RIGHT_CLICK_AIR ||  action.equals(Action.RIGHT_CLICK_BLOCK)){
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				
				
				
				if(cooldown.containsKey(player.getUniqueId())) {
					long secondsleft = ((cooldown.get(player.getUniqueId()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
					if (secondsleft > 0) {
						player.sendMessage(main.getConfig().getString("messages.cooldown").replace("%time%", String.valueOf(secondsleft)));
						ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown") .replace("%player%", player.getName() ));
						
						return;
					}
					if (secondsleft < 0) {
						cooldown.remove(player.getUniqueId());
						
					}
				}
				
				
				
				

				
				if(pvpdisabling.containsKey(player.getUniqueId())) {
					
					///																														CANCEL
					player.sendMessage(main.getConfig().getString("messages.cancel").replace("&", "§"));
					pvpdisabling.remove(player.getUniqueId());
					cooldown.put(player.getUniqueId(), System.currentTimeMillis());
					
					
					
					
					
					return;
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				pvpdisabling.put(player.getUniqueId(), System.currentTimeMillis());
				player.sendMessage(main.getConfig().getString("messages.disablingin5").replace("&", "§"));
				
				
				if(main.getConfig().getString("options.title") == "true") {
					
					Bukkit.dispatchCommand(console, "title "
							+player.getName()+ " times 20 70 20");
					Bukkit.dispatchCommand(console, "title "
							+player.getName()+ " subtitle {\"text\":\"5\"}");
					Bukkit.dispatchCommand(console, "title "
							+player.getName()+ " title {\"text\":\"Pvp Disabling\",\"bold\":true,\"color\":\"red\"}");
				}
				
				
				if(main.getConfig().getString("options.actionbar") == "true") {////																						
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.getConfig().getString("messages.abin5").replace("&", "§")));
				}
				
				
				
				
				
				if(main.getConfig().getString("options.sound") == "true") {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 2);
				
				}
				
				
					Timer chrono = new Timer();
				chrono.schedule(new TimerTask() {
					
					
					public void run() {
						if(!pvpdisabling.containsKey(player.getUniqueId())) {
							Bukkit.dispatchCommand(console, "title "
									+player.getName()+ " clear");
							return;
							
						}
						player.sendMessage(main.getConfig().getString("messages.disablingin4").replace("&", "§"));
						        
						        if(main.getConfig().getString("options.title") == "true") {
									Bukkit.dispatchCommand(console, "title "
											+player.getName()+ " subtitle {\"text\":\"4\"}");

								}
						        
						        
						        if(main.getConfig().getString("options.actionbar") == "true") {////																					
						        	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.getConfig().getString("messages.abin4").replace("&", "§")));
								}
						        
						        if(main.getConfig().getString("options.sound") == "true") {
						        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1.75F);
						        }
						
						}
					
					
				}, 1000);
				
				chrono.schedule(new TimerTask() {
					
					
					public void run() {
						if(!pvpdisabling.containsKey(player.getUniqueId())) {
							Bukkit.dispatchCommand(console, "title "
									+player.getName()+ " clear");
							return;
							
						}

						player.sendMessage(main.getConfig().getString("messages.disablingin3").replace("&", "§"));
						        
						        if(main.getConfig().getString("options.title") == "true") {
									Bukkit.dispatchCommand(console, "title "
											+player.getName()+ " subtitle {\"text\":\"3\"}");

								}
						        
						        if(main.getConfig().getString("options.actionbar") == "true") {////																						
						        	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.getConfig().getString("messages.abin3").replace("&", "§")));
								}
						        
						        
						        if(main.getConfig().getString("options.sound") == "true") {
						        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1.5F);
						        }
						
						}
					
					
				}, 2000);
				
				chrono.schedule(new TimerTask() {
					
					
					public void run() {
						if(!pvpdisabling.containsKey(player.getUniqueId())) {
							Bukkit.dispatchCommand(console, "title "
									+player.getName()+ " clear");
							return;
							
						}

						player.sendMessage(main.getConfig().getString("messages.disablingin2").replace("&", "§"));
						        
						        if(main.getConfig().getString("options.title") == "true") {
									Bukkit.dispatchCommand(console, "title "
											+player.getName()+ " subtitle {\"text\":\"2\"}");

								}
						        
						        
						        if(main.getConfig().getString("options.actionbar") == "true") {////																					
						        	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.getConfig().getString("messages.abin2").replace("&", "§")));
								}
						        
						        if(main.getConfig().getString("options.sound") == "true") {
						        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1.25F);
						        }
						
						}
					
					
				}, 3000);
		           
				chrono.schedule(new TimerTask() {
					
					
					public void run() {
						if(!pvpdisabling.containsKey(player.getUniqueId())) {
							Bukkit.dispatchCommand(console, "title "
									+player.getName()+ " clear");
							return;
							
						}

						player.sendMessage(main.getConfig().getString("messages.disablingin1").replace("&", "§"));
						        
						        if(main.getConfig().getString("options.title") == "true") {
									Bukkit.dispatchCommand(console, "title "
											+player.getName()+ " subtitle {\"text\":\"1\"}");

								}
						        
						        if(main.getConfig().getString("options.actionbar") == "true") {////																					
									player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.getConfig().getString("messages.abin1").replace("&", "§")));
								}
						        
						        if(main.getConfig().getString("options.sound") == "true") {
						        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						        }
						    
						
						}
					
					
				}, 4000);
				
				
				chrono.schedule(new TimerTask() {
					
					
					public void run() {							///																				END OF TIMER
						if(!pvpdisabling.containsKey(player.getUniqueId())) {
							return;
							
						}
						pvpenabled.remove(player.getUniqueId());
					
					player.sendMessage(main.getConfig().getString("messages.pvpdisabled").replace("&", "§"));
					
					if(main.getConfig().getString("options.title") == "true") {
						
						Bukkit.dispatchCommand(console, "title "
								+player.getName()+ " times 20 20 20");
						Bukkit.dispatchCommand(console, "title "
								+player.getName()+ " subtitle {\"text\":\"You disabled pvp\"}");
						Bukkit.dispatchCommand(console, "title "
								+player.getName()+ " title {\"text\":\"Pvp Disabled\",\"bold\":true,\"color\":\"red\"}");
					}
					
			        if(main.getConfig().getString("options.actionbar") == "true") {////																						
			        	player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.getConfig().getString("messages.abin").replace("&", "§")));
					}
					
					if(main.getConfig().getString("options.sound") == "true") {////																					
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
					}
					player.getInventory().setContents(inventory.get(player.getUniqueId()));
					pvpdisabling.remove(player.getUniqueId());
				
						    
						
						}
					
					
				}, 5000);
				
			
					
				}
				
				
				
				
				
				
			}
			}
				
				
				
				
				
				
			
			
	
	@EventHandler(priority = EventPriority.HIGHEST)					///																			On kill and death remove pvp
	public void ondeath(PlayerDeathEvent event){
		Player p = event.getEntity();
		Player a = event.getEntity().getKiller();
		if (!(p.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		pvpenabled.remove(p.getUniqueId(), null);
		
		if(main.getConfig().getString("options.heal") == "true") {
		a.setHealth(20);
		}
		
		if(main.getConfig().getString("options.Gapple") == "true") {
			a.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
			}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)																///    									RESPAWN GIVE OLD INV 
	public void onrespawn(PlayerRespawnEvent event){
		Player p =event.getPlayer();
		if (!(p.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		p.getInventory().setContents(inventory.get(p.getUniqueId()));
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST) 															
	public void onmovepvp(PlayerMoveEvent event){
		Player p =event.getPlayer();
		if (!(p.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		
		if(pvpenabled.containsKey(p.getUniqueId())) {///ACTION BAR PVP ACTIVATED
			if(pvpdisabling.containsKey(p.getUniqueId())) {
				return;
				
			}
			if(main.getConfig().getString("options.actionbar") == "true") {////																						PVP ENABLED ACTIONBAR
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent((main.getConfig().getString("messages.actionbar").replace("&", "§"))));
			}
		}
		
		
		
		
	}
	@EventHandler(priority = EventPriority.HIGHEST)																				///							ON JOIN GIVE SWORD AND REMOVE PVP
	public void onjoin(PlayerJoinEvent event){
		
		Player p =event.getPlayer();
		if (!(p.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		ItemStack C = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta checkpoint = C.getItemMeta();
		checkpoint.setDisplayName(main.getConfig().getString("messages.sword").replace("&", "§"));
		checkpoint.setLore(Arrays.asList("", (main.getConfig().getString("messages.swordlore").replace("&", "§"))));
		C.setItemMeta(checkpoint);
		p.getInventory().setItem((main.getConfig().getInt("options.slot")), C);
		pvpenabled.remove(p.getUniqueId(), null);
		
		
	}
	public void onArrowPickup(PlayerPickupItemEvent event){
		Player p = event.getPlayer();
		if (!(p.getWorld() == Bukkit.getWorld(main.getConfig().getString("enabledworlds.world").replace("&", "§")))) {
			return;
		}
		if(event.getItem() == new ItemStack(Material.ARROW)){
		if(!pvpenabled.containsKey(p.getUniqueId())) {
		event.setCancelled(true);
		}
		}
		}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void oncmdtest(PlayerCommandPreprocessEvent event){
		Player p = event.getPlayer();
		if(pvpenabled.containsKey(p.getUniqueId())) {
			
			if(main.getConfig().getString("commands.disabled.trueorfalse") == "true") {
				
			
			
			
		if(event.getMessage().contains(main.getConfig().getString("commands.disabled.1"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		if(event.getMessage().contains(main.getConfig().getString("commands.disabled.2"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		if(event.getMessage().contains(main.getConfig().getString("commands.disabled.3"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		if(event.getMessage().contains(main.getConfig().getString("commands.disabled.4"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		if(event.getMessage().contains(main.getConfig().getString("commands.disabled.5"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
			}
			
			
			if(main.getConfig().getString("commands.enabled.trueorfalse") == "true") {
		/// Enabling commands here
		if(!event.getMessage().contains(main.getConfig().getString("commands.enabled.1"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		
		if(!event.getMessage().contains(main.getConfig().getString("commands.enabled.2"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		
		if(!event.getMessage().contains(main.getConfig().getString("commands.enabled.3"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		
		if(!event.getMessage().contains(main.getConfig().getString("commands.enabled.4"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
		
		if(!event.getMessage().contains(main.getConfig().getString("commands.enabled.5"))) {
			p.sendMessage(main.getConfig().getString("messages.oncmdinpvp").replace("&", "§"));
			event.setCancelled(true);
		}
			}
		
		
		
		}
		
		
		
		
		
		
		
	
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	 public void onPlayerDeath1(PlayerDeathEvent e) {
        if(e.getEntity().getKiller() instanceof Player) {
            Player killer = (Player) e.getEntity().getKiller();
            Player killed = e.getEntity();
            if(killstreak.get(killer.getName()) != null){
                int k = killstreak.get(killer.getName()) + 1;
                killstreak.put(killer.getName(),k);
                if(killstreak.containsKey(killed.getName())) {
                    killstreak.remove(killed.getName());
                }
            }else{
                killstreak.put(killer.getName(), 3);
                Bukkit.broadcastMessage(ChatColor.GOLD + killer.getName() + " has a 3 killstreak!");
            }
        }
    }
	




}
