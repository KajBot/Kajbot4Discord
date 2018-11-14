package support.kajstech.kajbot.cc;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.cc.sites.Twitch;
import support.kajstech.kajbot.cc.sites.YouTube;
import support.kajstech.kajbot.utils.ConfigManager;

public class StreamAndVideoChecker extends ListenerAdapter {

    public static void run() {
        while (true) {
            try {
                if (!Bot.jda.getStatus().isInit()) Thread.sleep(10000);
            } catch (Exception ignored) {
            }

            try {
                if (ConfigManager.getConfig().getProperty("Twitch client ID").length() > 1 && ConfigManager.getConfig().getProperty("Twitch channels").length() > 1) {

                    String[] channels = ConfigManager.getProperty("Twitch channels").split(", ");
                    for (String c : channels) {
                        if (Twitch.checkIfOnline(c)) {
                            System.out.println(c + " is streaming " + Twitch.getGame() + " right now on Twitch!");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (ConfigManager.getConfig().getProperty("YouTube API key").length() > 1 && ConfigManager.getConfig().getProperty("YouTube channel ID").length() > 1) {
                    if (YouTube.checkIfOnline(ConfigManager.getProperty("YouTube channel ID"))) {
                        System.out.println(ConfigManager.getProperty("YouTube channel ID") + " is live on YouTube");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                System.out.println("Sleeping for 60 seconds");
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
