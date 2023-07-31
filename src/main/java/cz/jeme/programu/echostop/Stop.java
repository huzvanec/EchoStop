package cz.jeme.programu.echostop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class Stop {
    private final BukkitRunnable runnable;

    public Stop(int time) {
        runnable = new StopRunnable(time);
        runnable.runTaskTimer(EchoStop.getPlugin(EchoStop.class), 0L, 20L);
    }

    public void cancel() {
        runnable.cancel();
        Component title = Messages.from(EchoStop.config.getString("cancel.title"));
        Component subtitle = Messages.from(EchoStop.config.getString("cancel.subtitle"));
        long fadeIn = EchoStop.config.getLong("cancel.times.fade-in");
        long stay = EchoStop.config.getLong("cancel.times.stay");
        long fadeOut = EchoStop.config.getLong("cancel.times.fade-out");
        Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
        Title titleDisplay = Title.title(title, subtitle, times);



        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(titleDisplay);
        }
    }
}
