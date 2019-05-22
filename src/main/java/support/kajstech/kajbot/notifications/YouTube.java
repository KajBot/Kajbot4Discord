package support.kajstech.kajbot.notifications;

import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.utils.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static support.kajstech.kajbot.notifications.YouTube.readFromUrl;

class YouTube {

    static void check() throws IOException {
        if (Config.cfg.contains("YouTube API key") && Config.cfg.contains("YouTube channels")) {
            if (Config.cfg.get("YouTube video notifications").equalsIgnoreCase("true")) {
                for (String c : Config.cfg.get("YouTube channels").split(", ")) {
                    if (YouTubeVideo.check(c)) {
                        if (!YouTubeVideo.postedVideos.contains(getId(YouTubeVideo.channelUrl))) {
                            Bot.jda.getTextChannelById(Config.cfg.get("Notification channel ID")).sendMessage((Language.getMessage("YouTube.Video.POSTED_VIDEO")).replace("%CHANNEL%", getName(YouTubeVideo.channelUrl)) + "  https://www.youtube.com/watch?v=" + getId(YouTubeVideo.channelUrl)).queue();
                            YouTubeVideo.postedVideos.add(getId(YouTubeVideo.channelUrl));
                        }
                    } else {
                        YouTubeVideo.postedVideos.remove(getId(YouTubeVideo.channelUrl));
                    }
                }
            }
            if (Config.cfg.get("YouTube live notifications").equalsIgnoreCase("true")) {
                for (String c : Config.cfg.get("YouTube channels").split(", ")) {
                    if (YouTubeLive.check(c)) {
                        if (!YouTubeLive.liveChannels.contains(c)) {
                            Bot.jda.getTextChannelById(Config.cfg.get("Notification channel ID")).sendMessage((Language.getMessage("YouTube.Live.WENT_LIVE")).replace("%CHANNEL%", getName(YouTubeLive.channelUrl)) + "  https://www.youtube.com/watch?v=" + getId(YouTubeLive.channelUrl)).queue();
                            YouTubeLive.liveChannels.add(c);
                        }
                    } else {
                        YouTubeLive.liveChannels.remove(c);
                    }
                }
            }
        }
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
    static String channelUrl;
    static List<String> postedVideos = new ArrayList<>();

    static boolean check(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&maxResults=1&channelId=" + channel + "&key=" + Config.cfg.get("YouTube API key");
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").length() > 0;
    }

}

class YouTubeLive {
    static String channelUrl;
    static List<String> liveChannels = new ArrayList<>();

    static boolean check(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&eventType=live&maxResults=1&channelId=" + channel + "&key=" + Config.cfg.get("YouTube API key");
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").length() > 0;
    }

}
