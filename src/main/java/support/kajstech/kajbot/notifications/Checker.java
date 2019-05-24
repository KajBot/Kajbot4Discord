package support.kajstech.kajbot.notifications;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;

public class Checker extends ListenerAdapter {

    public static void run() throws InterruptedException {
        while (true) {
            //SITES
            try {
                if (Config.cfg.contains("Twitch client ID") && Config.cfg.contains("Twitch channels") && Config.cfg.get("Twitch live notifications").equalsIgnoreCase("true")) {
                    Twitch.check();
                }
                if (Config.cfg.contains("YouTube API key") && Config.cfg.contains("YouTube channels")) {
                    if (Config.cfg.get("YouTube video notifications").equalsIgnoreCase("true")) {
                        YouTubeVideo.check();
                    }
                    if (Config.cfg.get("YouTube live notifications").equalsIgnoreCase("true")) {
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
