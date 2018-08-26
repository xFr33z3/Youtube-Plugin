package freeze;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class yt extends JavaPlugin{
	
	public String gamemodeMessage = null;

	public void onEnable() {
		getLogger().info("Ciao a tutti, io sono un plugin per Youtube.");
		this.saveDefaultConfig();
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
		
		if(command.getName().equalsIgnoreCase("YoutubeReload")) {
			if(sender.hasPermission("yt.reload")) {
				reloadConfig();
				sender.sendMessage("The configuration file has been reloaded.");
			}else {
				sender.sendMessage(getConfig().getString("noPerm"));
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
