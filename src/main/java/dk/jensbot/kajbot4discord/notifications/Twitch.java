package dk.jensbot.kajbot4discord.notifications;

import dk.jensbot.kajbot4discord.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;

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
        channelUrl = "https://api.twitch.tv/kraken/streams/" + channel + "?client_id=" + Config.cfg.get("Twitch-client-ID");
        return !new JSONObject(readFromUrl(channelUrl)).isNull("stream");
    }


    static void check() throws IOException {
        for (String c : Config.cfg.get("Twitch-channels").split(", ")) {
            if (checkIfOnline(c)) {
                if (!live.contains(c)) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0x6441A5));
                    eb.setTitle((Language.lang.get("Twitch.WENT_LIVE")).replace("%CHANNEL%", c), new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getJSONObject("channel").getString("url"));
                    eb.addField(Language.lang.get("Twitch.TITLE"), new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getJSONObject("channel").getString("status"), false);
                    eb.addField(Language.lang.get("Twitch.NOW_PLAYING"), new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getString("game"), false);
                    eb.setImage("https://static-cdn.jtvnw.net/previews-ttv/live_user_" + new JSONObject(readFromUrl(channelUrl)).getJSONObject("stream").getJSONObject("channel").getString("name") + "-1920x1080.jpg");
                    eb.setTimestamp(ZonedDateTime.now());
                    Bot.jda.getTextChannelById(Config.cfg.get("Notification-channel-ID")).sendMessage(eb.build()).queue();
                    live.add(c);
                }
            } else {
                live.remove(c);
            }
        }
    }

}
