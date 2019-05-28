package dk.jensbot.kajbot4discord.notifications;

import dk.jensbot.kajbot4discord.Bot;
import org.json.JSONObject;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class YouTubeLive {
    private static List<String> liveChannels = new ArrayList<>();
    private static String channelUrl;

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(
                page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    static String getId() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    static String getName() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&eventType=live&maxResults=1&channelId=" + channel + "&key=" + Config.cfg.get("YouTube-API-key");
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").length() > 0;
    }

    static void check() throws IOException {
        for (String c : Config.cfg.get("YouTube-channels").split(", ")) {
            if (checkIfOnline(c)) {
                if (!YouTubeLive.liveChannels.contains(c)) {
                    Bot.jda.getTextChannelById(Config.cfg.get("Notification-channel-ID")).sendMessage((Language.lang.get("YouTube.Live.WENT_LIVE")).replace("%CHANNEL%", getName()) + "  https://www.youtube.com/watch?v=" + getId()).queue();
                    YouTubeLive.liveChannels.add(c);
                }
            } else {
                YouTubeLive.liveChannels.remove(c);
            }
        }
    }
}
