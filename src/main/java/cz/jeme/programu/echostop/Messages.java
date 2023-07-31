package cz.jeme.programu.echostop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class Messages {
    private Messages() {
    }
    public static final MiniMessage MESSAGE = MiniMessage.miniMessage();
    public static final String PREFIX = "<dark_gray>[<gold>EchoStop</gold>]:</dark_gray> ";

    public static Component from(String string) {
        return MESSAGE.deserialize(string);
    }
    public static Component prefix(String string) {
        return from(PREFIX + string);
    }
}
