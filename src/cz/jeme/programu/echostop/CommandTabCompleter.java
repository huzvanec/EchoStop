package cz.jeme.programu.echostop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> list = new ArrayList<String>();
			list.addAll(EchoStop.CORRECT_ARGS.values());
			return list;
		}
		return new ArrayList<String>();
	}

}
