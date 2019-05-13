package support.kajstech.kajbot.notifications;

import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Twitch {

    private static List<String> live = new ArrayList<>();
    private static String channelUrl;

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(
                page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://api.twitch.tv/kraken/streams/" + channel + "?client_id=" + ConfigHandler.getProperty("Twitch client ID");
        return !new JSONObject(readFromUrl(channelUrl)).isNull("stream");
    }

    private static String getGame() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getString("game");
    }

    private static String getThumbnail() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getJSONObject("preview").getString("large");
    }

    private static String getTitle() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getJSONObject("channel").getString("status");
    }

    static void check() throws IOException {
        if (ConfigHandler.containsProperty("Twitch client ID") && ConfigHandler.containsProperty("Twitch channels") && ConfigHandler.getProperty("Twitch live notifications").equalsIgnoreCase("true")) {
            for (String c : ConfigHandler.getProperty("Twitch channels").split(", ")) {
                if (checkIfOnline(c)) {
                    if (!live.contains(c)) {
                        live.add(c);
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(new Color(0x6441A5));
                        eb.setTitle((Language.getMessage("Twitch.WENT_LIVE")).replace("%CHANNEL%", c), "https://www.twitch.tv/" + c);
                        eb.addField(Language.getMessage("Twitch.TITLE"), getTitle(), false);
                        eb.addField(Language.getMessage("Twitch.NOW_PLAYING"), getGame(), false);
                        eb.setImage(getThumbnail());
                        eb.setTimestamp(ZonedDateTime.now());
                        Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                    }
                } else {
                    live.remove(c);
                }
            }
        }
    }

}
