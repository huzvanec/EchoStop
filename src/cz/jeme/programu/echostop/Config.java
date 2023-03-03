package cz.jeme.programu.echostop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class Config {
	private File dataFolder;

	private File configFile;
	private FileConfiguration configFileYaml;

	private static final String CONFIG_FILE_NAME = "config.yml";
	
	private static final Map<String, String> CONFIG_NAMES = new HashMap<String, String>();

	public int timerCooldown;
	public String closingMessage;
	public String kickMessage;
	public String zeroMessage;
	public List<ChatColor> colors;
	public int lastColorsCount;
	public ChatColor lastColorsColor;
	public String cancelMessage;
	
	{
		CONFIG_NAMES.put("COOLDOWN", "Timer cooldown");
		CONFIG_NAMES.put("CLOSING_MESSAGE", "Closing message");
		CONFIG_NAMES.put("KICK_MESSAGE", "Kick message");
		CONFIG_NAMES.put("ZERO_MESSAGE", "Message at zero");
		CONFIG_NAMES.put("COLORS", "Timer colors");
		CONFIG_NAMES.put("LAST_COLORS_COUNT", "Static colors.Count");
		CONFIG_NAMES.put("LAST_COLORS_COLOR", "Static colors.Color");
		CONFIG_NAMES.put("CANCEL_MESSAGE", "Cancel message");
	}

	public Config(File dataFolder) {
		this.dataFolder = dataFolder;
		refreshConfig();
	}

	public void refreshConfig() {
		refreshConfigVars();
		if (!configFile.exists()) {
			try {
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}
				configFile.createNewFile();
			} catch (IOException e) {
				EchoStop.serverLog(Level.SEVERE, "Unable to create config file! EchoStop will not work properly!");
				return;
			}
			setDefaults();
			try {
				configFileYaml.save(configFile);
			} catch (IOException e) {
				EchoStop.serverLog(Level.SEVERE, "Unable to save	 config file! EchoStop will not work properly!");
				return;
			}
		}
			timerCooldown = configFileYaml.getInt(CONFIG_NAMES.get("COOLDOWN"));
			String kickMessageRead = configFileYaml.getString(CONFIG_NAMES.get("KICK_MESSAGE"));
			kickMessage = translateColor(kickMessageRead);
			closingMessage = configFileYaml.getString(CONFIG_NAMES.get("CLOSING_MESSAGE"));
			colors = new ArrayList<ChatColor>();
			for (String color : configFileYaml.getStringList(CONFIG_NAMES.get("COLORS"))) {
				colors.add(0, ChatColor.of(color));
			}
			lastColorsCount = configFileYaml.getInt(CONFIG_NAMES.get("LAST_COLORS_COUNT"));
			String lastColorsColorRead = configFileYaml.getString(CONFIG_NAMES.get("LAST_COLORS_COLOR"));
			lastColorsColor = ChatColor.of(lastColorsColorRead);
			cancelMessage = configFileYaml.getString(CONFIG_NAMES.get("CANCEL_MESSAGE"));
			
	}

	public void refreshConfigVars() {
		configFile = new File(dataFolder + File.separator + CONFIG_FILE_NAME);
		configFileYaml = YamlConfiguration.loadConfiguration(configFile);
	}

	public void setDefaults() {
		set(CONFIG_NAMES.get("COOLDOWN"), 60);
		
		set(CONFIG_NAMES.get("CLOSING_MESSAGE"), "Server closing...");
		set(CONFIG_NAMES.get("KICK_MESSAGE"), "§4Server closed!");
		set(CONFIG_NAMES.get("ZERO_MESSAGE"), "§4Server closed!");
		set(CONFIG_NAMES.get("CANCEL_MESSAGE"), "§2Canceled!");
		
		set(CONFIG_NAMES.get("LAST_COLORS_COUNT"), 3);
		set(CONFIG_NAMES.get("LAST_COLORS_COLOR"), "DARK_RED");
		
		List<String> colors = new ArrayList<String>();
		colors.add("GREEN");
		colors.add("YELLOW");
		colors.add("GOLD");
		colors.add("RED");
		set(CONFIG_NAMES.get("COLORS"), colors);
	}
	
	private void set(String key, Object value) {
		configFileYaml.set(key, value);
	}
	
	private String translateColor(String text) {
		String paragraph = ChatColor.translateAlternateColorCodes('§', text);
		String ampersand = ChatColor.translateAlternateColorCodes('&', paragraph);
		return ampersand;
	}
}
