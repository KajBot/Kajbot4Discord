package support.kajstech.kajbot.notifications;

import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static support.kajstech.kajbot.notifications.YouTube.*;

public class YouTube {

    public static void check() throws IOException {
        YouTubeLive.check();
        YouTubeVideo.check();
    }

    static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    static String getId(String channel) throws IOException {
        return new JSONObject(readFromUrl(channel)).getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    static String getName(String channel) throws IOException {
        return new JSONObject(readFromUrl(channel)).getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }
}

class YouTubeVideo {

    private static String channelUrl;
    private static List<String> video = new ArrayList<>();

    private static boolean checkVideo(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&maxResults=1&channelId=" + channel + "&key=" + ConfigHandler.getProperty("YouTube API key");
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").length() > 0;
    }


    static void check() throws IOException {
        if (ConfigHandler.containsProperty("YouTube API key") && ConfigHandler.containsProperty("YouTube channels") && ConfigHandler.getProperty("YouTube video notifications").equalsIgnoreCase("true")) {
            for (String c : ConfigHandler.getProperty("YouTube channels").split(", ")) {
                if (checkVideo(c)) {
                    if (!video.contains(getId(channelUrl))) {
                        video.add(getId(channelUrl));
                        Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage((Language.getMessage("YouTube.Video.POSTED_VIDEO")).replace("%CHANNEL%", getName(channelUrl)) + "  https://www.youtube.com/watch?v=" + getId(channelUrl)).queue();
                    }
                } else {
                    video.remove(getId(channelUrl));
                }
            }
        }
    }

}

class YouTubeLive {
    private static List<String> liveYoutube = new ArrayList<>();
    private static String channelUrl;

    private static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&eventType=live&maxResults=1&channelId=" + channel + "&key=" + ConfigHandler.getProperty("YouTube API key");
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").length() > 0;
    }


    static void check() throws IOException {
        if (ConfigHandler.containsProperty("YouTube API key") && ConfigHandler.containsProperty("YouTube channels") && ConfigHandler.getProperty("YouTube live notifications").equalsIgnoreCase("true")) {
            for (String c : ConfigHandler.getProperty("YouTube channels").split(", ")) {
                if (checkIfOnline(c)) {
                    if (!liveYoutube.contains(c)) {
                        liveYoutube.add(c);
                        Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage((Language.getMessage("YouTube.Live.WENT_LIVE")).replace("%CHANNEL%", getName(channelUrl)) + "  https://www.youtube.com/watch?v=" + getId(channelUrl)).queue();
                    }
                } else {
                    liveYoutube.remove(c);
                }
            }
        }
    }

}
