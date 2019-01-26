package support.kajstech.kajbot.cc;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.cc.sites.Twitch;
import support.kajstech.kajbot.cc.sites.YouTube;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.IOException;

public class StreamAndVideoChecker extends ListenerAdapter {

    public static void run() throws InterruptedException {

        while (true) {
            if (!ConfigHandler.containsProperty("Notification channel ID")) return;

            try {
                if (Bot.jda.getStatus() != JDA.Status.CONNECTED) Thread.sleep(5000);
            } catch (Exception ignored) {
                Thread.sleep(5000);
            }


            //SITES
            try {
                Twitch.checkTwitch();
                YouTube.checkYouTube();
            } catch (IOException e) {
                LogHelper.error(StreamAndVideoChecker.class, e.toString());
                e.printStackTrace();
            }


            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                LogHelper.error(StreamAndVideoChecker.class, e.toString());
            }
        }


    }
}