package cz.jeme.programu.echostop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class StopRunnable extends BukkitRunnable {
    public int timer;
    public final int time;
    private final String title = EchoStop.config.getString("stop.title");
    private final String subtitle = EchoStop.config.getString("stop.subtitle");
    private static final String TIME_PLACEHOLDER = "{TIME}";
    private static final String PHASE_PLACEHOLDER = "{PHASE}";

    public StopRunnable(int time) {
        this.time = time;
        timer = time;
    }

    @Override
    public void run() {
        if (timer == -1) {
            cancel();
            shutdown();
            return;
        }

        float phase = (float) timer / time;
        assert title != null;
        String titleString = title.replace(TIME_PLACEHOLDER, String.valueOf(timer));
        titleString = titleString.replace(PHASE_PLACEHOLDER, String.valueOf(phase));

        Component titleComponent = Messages.from(titleString);

        assert subtitle != null;
        String subtitleString = subtitle.replace(TIME_PLACEHOLDER, String.valueOf(timer));
        subtitleString = subtitleString.replace(PHASE_PLACEHOLDER, String.valueOf(phase));
        Component subtitleComponent = Messages.from(subtitleString);

        Title.Times times = Title.Times.times(Duration.ZERO, Duration.ofDays(1L), Duration.ZERO);
        Title titleDisplay = Title.title(titleComponent, subtitleComponent, times);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(titleDisplay);
        }
        timer--;
    }

    private void shutdown() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kick(Messages.from(EchoStop.config.getString("stop.kick-message")));
        }
        Bukkit.shutdown();
    }
}
