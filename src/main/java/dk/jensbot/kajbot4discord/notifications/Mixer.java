package dk.jensbot.kajbot4discord.notifications;

import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;

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

class Mixer {

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
        channelUrl = "https://mixer.com/api/v1/channels/" + channel;
        return new JSONObject(readFromUrl(channelUrl)).getBoolean("online");
    }


    static void check() throws IOException {
        for (String c : Config.cfg.get("Mixer.channels").split(", ")) {
            if (checkIfOnline(c)) {
                if (!live.contains(c)) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0x6441A5));
                    eb.setTitle((Language.lang.get("Mixer.WENT_LIVE")).replace("%CHANNEL%", c), "https://mixer.com/" + c);
                    eb.addField(Language.lang.get("Mixer.TITLE"), new JSONObject(readFromUrl(channelUrl)).getString("name"), false);
                    eb.addField(Language.lang.get("Mixer.NOW_PLAYING"), new JSONObject(readFromUrl(channelUrl)).getJSONObject("type").getString("name"), false);
                    eb.setImage(new JSONObject(readFromUrl(channelUrl)).getJSONObject("type").getString("coverUrl"));
                    eb.setTimestamp(ZonedDateTime.now());
                    Bot.jda.getTextChannelById(Config.cfg.get("Notifications.channelID")).sendMessage(eb.build()).queue();
                    live.add(c);
                }
            } else {
                live.remove(c);
            }
        }
    }

}
