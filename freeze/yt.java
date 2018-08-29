package freeze;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class yt extends JavaPlugin implements Listener{
	
	public String gamemodeMessage = null;
	public boolean onMoveEnabled = false;

	public void onEnable() {
		getLogger().info("Ciao a tutti, io sono un plugin per Youtube.");
		getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.SAND) {
			event.getBlock().getLocation().getWorld().playEffect(event.getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
			event.getPlayer().sendMessage(getConfig().getString("sandBreak"));
		}
		if(event.getBlock().getTypeId() == 81) {
			for(int i = 0; i < 50; i++) {
				event.getBlock().getLocation().getWorld().playEffect(event.getBlock().getLocation(), Effect.SMOKE, 1);
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(onMoveEnabled) {
			if(event.getPlayer().getLocation().getBlock().isLiquid()) {
				event.getPlayer().getLocation().getWorld().playEffect(event.getPlayer().getLocation().getBlock().getLocation(), Effect.ENDER_SIGNAL, 10);
				try{event.getPlayer().setHealth(event.getPlayer().getHealth()+0.1);}
				catch(Exception e) {}
				Random rnd = new Random();
				if(rnd.nextInt(20) == 4) {
				event.getPlayer().getLocation().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SQUID);
				}
				event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel()+1);
				event.getPlayer().giveExp(1);
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[]args) {
		Player player = (Player) sender;
		if(command.getName().equalsIgnoreCase("fly")) {
			if(sender.hasPermission("yt.fly")) {
			if(player.getAllowFlight()) {
				player.setAllowFlight(false);
				sender.sendMessage("§3Now you cannot fly.");
			}else {
				player.setAllowFlight(true);
				sender.sendMessage("§3Now you can fly.");
			}
			
			}else {
				sender.sendMessage(getConfig().getString("noPerm"));
			}
			return true;
		}
		
		if(command.getName().equalsIgnoreCase("enableOnMove")) {
			if(onMoveEnabled) {
				onMoveEnabled = false;
				sender.sendMessage("onMove Event disabled");
			}else {
				onMoveEnabled = true;
				sender.sendMessage("onMove Event enabled");
			}
		}
		
		if(command.getName().equalsIgnoreCase("YoutubeReload")) {
			if(sender.hasPermission("yt.reload")) {
				reloadConfig();
				sender.sendMessage("The configuration file has been reloaded.");
			}else {
				sender.sendMessage(getConfig().getString("noPerm"));
			}
		}
		
		if(command.getName().equalsIgnoreCase("damageme")) {
			try {
			if(sender.hasPermission("yt.damage")) {
				double damageme = Double.parseDouble(args[0]);
				player.damage(damageme);
				sender.sendMessage("You are very stupid.");
			}else {
				sender.sendMessage(getConfig().getString("noPerm"));
			}
			}catch(Exception e) {
				sender.sendMessage("§bUsage: /DamageMe <double, ex 1.5>");
			}
		}
		
		
		if(command.getName().equalsIgnoreCase("gm")) {
			try {
				if(args[0].equalsIgnoreCase("1")) {
					if(sender.hasPermission("yt.gm.creative")) {
					gamemodeMessage = getConfig().getString("gamemodeMessage");
					gamemodeMessage = gamemodeMessage.replaceAll("%mode%", "CREATIVE");
					player.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(gamemodeMessage);
					}else {
						sender.sendMessage(getConfig().getString("noPerm"));
					}
					return true;
				}
				
				if(args[0].equalsIgnoreCase("0")) {
					if(sender.hasPermission("yt.gm.survival")) {
					gamemodeMessage = getConfig().getString("gamemodeMessage");
					gamemodeMessage = gamemodeMessage.replaceAll("%mode%", "SURVIVAL");
					player.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(gamemodeMessage);
					}else {
						sender.sendMessage(getConfig().getString("noPerm"));
					}
					return true;
				}
				
				if(args[0].equalsIgnoreCase("2")) {
					if(sender.hasPermission("yt.gm.adventure")) {
					gamemodeMessage = getConfig().getString("gamemodeMessage");
					gamemodeMessage = gamemodeMessage.replaceAll("%mode%", "ADVENTURE");
					player.setGameMode(GameMode.ADVENTURE);
					sender.sendMessage(gamemodeMessage);
					}else {
						sender.sendMessage(getConfig().getString("noPerm"));
					}
					return true;
				}
				
				if(args[0].equalsIgnoreCase("3")) {
					gamemodeMessage = getConfig().getString("gamemodeMessage");
			     	gamemodeMessage = gamemodeMessage.replaceAll("%mode%", "SPECTATOR");
					if(sender.hasPermission("yt.gm.spectator")) {
					player.setGameMode(GameMode.SPECTATOR);
					sender.sendMessage(gamemodeMessage);
					}else {
						sender.sendMessage(getConfig().getString("noPerm"));
					}
					return true;
				}
			}catch(Exception e) {
				sender.sendMessage("§bUsage: /gm <mode>");
			}
			
		}
		return false;
	}
	
}
