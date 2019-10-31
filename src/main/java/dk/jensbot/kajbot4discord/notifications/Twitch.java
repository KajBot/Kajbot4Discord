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
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

class Twitch {
    private static List<String> live = new ArrayList<>();
    private static String channelUrl;

    private static JSONObject readFromUrl(String url) throws IOException {
        JSONObject responseJson;

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("Client-ID", Config.cfg.get("Twitch.clientID"));

        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            responseJson = new JSONObject(response.toString());
        }
        return responseJson;
    }

    private static String getUserId(String channel) throws IOException {
        return readFromUrl("https://api.twitch.tv/helix/users?login=" + channel).getJSONArray("data").getJSONObject(0).getString("id");
    }

    private static boolean isOnline(String channel) throws IOException {
        channelUrl = "https://api.twitch.tv/helix/streams?user_id=" + getUserId(channel);
        return !readFromUrl(channelUrl).getJSONArray("data").isNull(0);
    }

    static void check() throws IOException {
        for (String c : Config.cfg.get("Twitch.channels").split(", ")) {
            if (isOnline(c)) {
                if (!live.contains(c)) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0x6441A5));
                    eb.setTitle((Language.lang.get("Twitch.WENT_LIVE")).replace("%CHANNEL%", readFromUrl(channelUrl).getJSONArray("data").getJSONObject(0).getString("user_name")), "https://twitch.tv/" + readFromUrl(channelUrl).getJSONArray("data").getJSONObject(0).getString("user_name"));
                    eb.addField(Language.lang.get("Twitch.TITLE"), readFromUrl(channelUrl).getJSONArray("data").getJSONObject(0).getString("title"), false);
                    eb.addField(Language.lang.get("Twitch.NOW_PLAYING"), readFromUrl("https://api.twitch.tv/helix/games?id=" + readFromUrl(channelUrl).getJSONArray("data").getJSONObject(0).getString("game_id")).getJSONArray("data").getJSONObject(0).getString("name"), false);
                    eb.setImage("https://static-cdn.jtvnw.net/previews-ttv/live_user_" + c);
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
