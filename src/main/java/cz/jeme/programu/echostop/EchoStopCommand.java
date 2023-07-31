package cz.jeme.programu.echostop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EchoStopCommand extends Command {
    private Stop stop = null;

    protected EchoStopCommand() {
        super(
                "echostop",
                "The main command of EchoStop",
                "false",
                List.of("estop")
        );
        setPermission("echostop.echostop");
        register();
    }

    private void register() {
        Bukkit.getCommandMap().register("echostop", this);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length == 0) {
            stop(sender);
            return true;
        }
        if (args[0].equals("stop")) {
            if (args.length > 1) {
                int time;
                try {
                    time = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(Messages.prefix("<red>That is not a valid number!</red>"));
                    return true;
                }
                if (time < 1) {
                    sender.sendMessage(Messages.prefix("<red>Time must be at least 1 second!</red>"));
                    return true;
                }
                stop(sender, time);
                return true;
            }
            stop(sender);
            return true;
        }
        if (args[0].equals("reload")) {
            EchoStop.reload();
            sender.sendMessage(Messages.prefix("<green>Configuration reloaded successfully!</green>"));
            return true;
        }
        if (args[0].equals("cancel")) {
            cancel(sender);
            return true;
        }
        sender.sendMessage(Messages.prefix("<red>Unknown command!</red>"));
        return true;
    }

    private void stop(CommandSender sender, int time) {
        if (stop == null) {
            stop = new Stop(time);
        } else {
            sender.sendMessage(Messages.prefix("<red>The server is already stopping!</red>"));
        }
    }

    private void stop(CommandSender sender) {
        stop(sender, EchoStop.config.getInt("stop.timer"));
    }

    private void cancel(CommandSender sender) {
        if (stop != null) {
            stop.cancel();
            stop = null;
        } else {
            sender.sendMessage(Messages.prefix("<red>The server is not stopping!</red>"));
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return containsFilter(List.of("stop", "cancel", "reload"), args[0]);
        }
        return Collections.emptyList();
    }

    private List<String> containsFilter(List<String> list, String mark) {
        List<String> filtered = new ArrayList<>();
        for (String string : list) {
            if (string.contains(mark)) filtered.add(string);
        }
        return filtered;
    }
}
