package support.kajstech.kajbot.cc;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.cc.sites.Twitch;
import support.kajstech.kajbot.cc.sites.YouTube;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class StreamAndVideoChecker extends ListenerAdapter {

    public static void run() throws InterruptedException {

        while (true) {
            try {
                if (!Bot.jda.getStatus().isInit()) Thread.sleep(10000);
            } catch (Exception ignored) {
                Thread.sleep(10000);
            }

            if (!ConfigHandler.containsProperty("Notification channel ID")) return;


            //SITES
            Twitch.checkTwitch();
            YouTube.checkYouTube();


            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}