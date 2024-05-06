package fr.biigbossz.main;

import org.bukkit.plugin.java.JavaPlugin;





public class main extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new pvp_1_10_R1(this),this);
		getServer().getPluginManager().registerEvents(new guimenu(), this);
		
		getCommand("lobbyfighting").setExecutor(new fight(this));
		getCommand("lf").setExecutor(new fight(this));
		
	}

}
