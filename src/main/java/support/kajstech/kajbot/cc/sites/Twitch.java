package support.kajstech.kajbot.cc.sites;

import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Twitch {

    private static List<String> liveTwitch = new ArrayList<>();

    private static String channelUrl;

    private static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://api.twitch.tv/kraken/streams/" + channel + "?client_id=" + ConfigHandler.getProperty("Twitch client ID");

        String jsonText = readFromUrl(channelUrl);// reads text from URL
        JSONObject json = new JSONObject(jsonText);

        return !json.isNull("stream");
    }

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private static String getGame() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONObject("stream").getString("Game");
    }

    private static String getThumbnail() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONObject("stream").getJSONObject("preview").getString("large");
    }

    private static String getTitle() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONObject("stream").getJSONObject("channel").getString("status");
    }


    public static void checkTwitch() {
        try {
            if (ConfigHandler.containsProperty("Twitch client ID") && ConfigHandler.containsProperty("Twitch channels")) {
                for (String c : ConfigHandler.getProperty("Twitch channels").split(", ")) {
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
                            Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                        }
                    } else {
                        liveTwitch.remove(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
