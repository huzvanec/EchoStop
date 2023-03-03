package cz.jeme.programu.echostop;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class StopTimer extends BukkitRunnable {

	private int startTime;
	private int timer;

	private Config config;

	public StopTimer(int startTime, Config config) {
		this.startTime = startTime;
		this.timer = startTime;
		this.config = config;
	}

	@Override
	public void run() {
		if (timer <= -1) { // Let him display 0 before stopping
			cancel();
			stop();
			return;
		}

		int colorsCount = config.colors.size();

		float quadrant = (startTime + 1F) / colorsCount;
		int colorId = (int) Math.floor(timer / quadrant);
		
		ChatColor color = config.colors.get(colorId);
		
		if (timer <= config.lastColorsCount) {
			color = config.lastColorsColor;
		}

		if (timer != 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendTitle(color + String.valueOf(timer), color + config.closingMessage, 0, 100, 20);
			}
		} else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendTitle(color + config.closingMessage, "", 0, 100, 20);
			}
		}

		timer--;
	}

	private void stop() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(config.kickMessage);
		}
		Bukkit.shutdown();
	}

}
