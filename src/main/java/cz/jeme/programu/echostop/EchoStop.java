package cz.jeme.programu.echostop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class EchoStop extends JavaPlugin {
	public static FileConfiguration config;

	@Override
	public void onEnable() {
		new EchoStopCommand(); // register the /echostop command
		saveDefaultConfig();
		reload();
	}


	public static void reload() {
		EchoStop plugin = EchoStop.getPlugin(EchoStop.class);
		plugin.reloadConfig();
		config = plugin.getConfig();
	}
}