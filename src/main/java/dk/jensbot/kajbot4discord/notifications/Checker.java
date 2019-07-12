package dk.jensbot.kajbot4discord.notifications;

import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.LogHelper;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;

public class Checker extends ListenerAdapter {

    public static void run() throws InterruptedException {
        while (true) {
            //SITES
            try {
                if (Config.cfg.hasValue("Twitch.clientID") && Config.cfg.hasValue("Twitch.channels") && Config.cfg.get("Twitch.notifications").equalsIgnoreCase("true")) {
                    Twitch.check();
                }
                if (Config.cfg.hasValue("YouTube.key") && Config.cfg.hasValue("YouTube.channels")) {
                    if (Config.cfg.get("YouTube.videoNotifications").equalsIgnoreCase("true")) {
                        YouTubeVideo.check();
                    }
                    if (Config.cfg.get("YouTube.liveNotifications").equalsIgnoreCase("true")) {
                        YouTubeLive.check();
                    }
                }
            } catch (IOException e) {
                LogHelper.error(Checker.class, e.toString());
            }
            Thread.sleep(60000);
        }

    }
}
