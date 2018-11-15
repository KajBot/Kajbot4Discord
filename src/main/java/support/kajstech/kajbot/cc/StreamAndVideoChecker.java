package support.kajstech.kajbot.cc;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.cc.sites.Twitch;
import support.kajstech.kajbot.cc.sites.YouTube;
import support.kajstech.kajbot.utils.ConfigManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StreamAndVideoChecker extends ListenerAdapter {

    public static void run() throws InterruptedException {
        List<String> liveTwitch = new ArrayList<>();
        List<String> liveYoutube = new ArrayList<>();

        while (true) {
            try {
                if (!Bot.jda.getStatus().isInit()) Thread.sleep(10000);
            } catch (Exception ignored) {
                Thread.sleep(10000);
            }

            if (ConfigManager.getProperty("Notification channel ID").length() < 2) {
                return;
            }

            try {
                if (ConfigManager.getProperty("Twitch client ID").length() > 1 && ConfigManager.getProperty("Twitch channels").length() > 1) {
                    for (String c : ConfigManager.getProperty("Twitch channels").split(", ")) {
                        if (Twitch.checkIfOnline(c)) {
                            if (!liveTwitch.contains(c)) {
                                liveTwitch.add(c);
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setColor(new Color(0xA6C055));
                                eb.setTitle("Title", null);
                                eb.setDescription(Twitch.getTitle());
                                eb.addField("Now Playing", Twitch.getGame(), false);
                                eb.setAuthor(c + " just went live on Twitch!", "https://www.twitch.tv/" + c, null);
                                eb.setImage(Twitch.getThumbnail());
                                Bot.jda.getTextChannelById(ConfigManager.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                            }
                        } else {
                            liveTwitch.remove(c);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (ConfigManager.getProperty("YouTube API key").length() > 1 && ConfigManager.getProperty("YouTube channels").length() > 1) {
                    for (String c : ConfigManager.getProperty("YouTube channels").split(", ")) {
                        if (YouTube.checkIfOnline(c)) {
                            if (!liveYoutube.contains(c)) {
                                liveYoutube.add(c);
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setColor(new Color(0xA6C055));
                                eb.setTitle("Title", null);
                                eb.setDescription(YouTube.getTitle());
                                eb.setAuthor(YouTube.getName() + " just went live on YouTube!", "https://youtu.be/" + YouTube.getId(), null);
                                eb.setImage(YouTube.getThumbnail());
                                Bot.jda.getTextChannelById(ConfigManager.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                            }
                        } else {
                            liveYoutube.remove(c);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}