package cz.jeme.programu.echostop;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class EchoStop extends JavaPlugin {

	public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "EchoStop"
			+ ChatColor.DARK_GRAY + "]: ";

	private Config config;

	private StopTimer stopTimer = null;

	public static final Map<String, String> CORRECT_ARGS = new HashMap<String, String>();

	{
		CORRECT_ARGS.put("RELOAD", "reload");
		CORRECT_ARGS.put("STOP", "stop");
		CORRECT_ARGS.put("CANCEL", "cancel");
	}

	@Override
	public void onEnable() {
		config = new Config(getDataFolder());
		getCommand("echostop").setTabCompleter(new CommandTabCompleter());
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("echostop")) {
			if (args.length == 0) {
				stopCommand(config.timerCooldown, sender);
				return true;
			}
			if (args[0].equals(CORRECT_ARGS.get("RELOAD"))) {
				if (args.length > 1) {
					sender.sendMessage(PREFIX + ChatColor.RED + "Too many arguments!");
					return true;
				}
				reloadCommand(sender);
				return true;
			}
			if (args[0].equals(CORRECT_ARGS.get("STOP"))) {
				if (args.length == 1) {
					stopCommand(config.timerCooldown, sender);
					return true;
				}
				if (args.length == 2) {
					int cooldown;
					try {
						cooldown = Integer.valueOf(args[1]);
					} catch (Exception e) {
						sender.sendMessage(PREFIX + ChatColor.RED + "That is not a valid number!");
						return true;
					}
					if (cooldown < 1) {
						sender.sendMessage(PREFIX + ChatColor.RED + "Cooldown needs to be higher than zero!");
						return true;
					}
					stopCommand(cooldown, sender);
					return true;
				}
				sender.sendMessage(PREFIX + ChatColor.RED + "Too many arguments!");
				return true;
			}
			if (args[0].equals(CORRECT_ARGS.get("CANCEL"))) {
				cancelCommand(sender);
				return true;
			}
			sender.sendMessage(PREFIX + ChatColor.RED + "Unknown command!");
			return true;
		}
		return false;

	}

	private void reloadCommand(CommandSender sender) {
		config.refreshConfig();
		sender.sendMessage(PREFIX + ChatColor.GREEN + "Config reloaded!");
	}

	private void stopCommand(int timer, CommandSender sender) {
		if (stopTimer != null) {
			sender.sendMessage(PREFIX + ChatColor.RED + "The server is already stopping!");
			return;
		}
		stopTimer = new StopTimer(timer, config);
		stopTimer.runTaskTimer(this, 0L, 20L);
		sender.sendMessage(
				PREFIX + ChatColor.GREEN + "Stopping server in " + timer + " seconds!");
	}

	private void cancelCommand(CommandSender sender) {
		if (stopTimer == null) {
			sender.sendMessage(PREFIX + ChatColor.RED + "There is nothing to cancel!");
			return;
		}
		stopTimer.cancel();
		stopTimer = null;
		sender.sendMessage(PREFIX + ChatColor.GREEN + "Stopping canceled!");
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendTitle(config.cancelMessage, "", 0, 20, 20);
		}
	}

	public static void serverLog(Level level, String message) {
		Bukkit.getServer().getLogger().log(level, ChatColor.stripColor(PREFIX) + message);
	}
}