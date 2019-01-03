package support.kajstech.kajbot.cc.sites;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static support.kajstech.kajbot.cc.sites.YouTube.*;

public class YouTube {

    public static void checkYouTube() throws IOException {
        YouTubeLive.check();
        YouTubeVideo.check();
    }

    static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    static String getTitle(String channel) throws IOException {
        String jsonChannels = readFromUrl(channel);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
    }

    static String getDescription(String channel) throws IOException {
        String jsonChannels = readFromUrl(channel);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description");
    }

    static String getId(String channel) throws IOException {
        String jsonChannels = readFromUrl(channel);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    static String getName(String channel) throws IOException {
        String jsonChannels = readFromUrl(channel);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    static String getThumbnail(String channel) throws IOException {
        String jsonChannels = readFromUrl(channel);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
    }
}

class YouTubeVideo {

    private static String channelUrl;

    private static List<String> video = new ArrayList<>();

    private static boolean checkVideo(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&maxResults=1&channelId=" + channel + "&key=" + ConfigHandler.getProperty("YouTube API key");

        String jsonText = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonText);

        return json.getJSONArray("items").length() > 0;
    }


    static void check() throws IOException {
        if (ConfigHandler.containsProperty("YouTube API key") && ConfigHandler.containsProperty("YouTube channels") && ConfigHandler.getProperty("YouTube video notifications").equalsIgnoreCase("true")) {
            for (String c : ConfigHandler.getProperty("YouTube channels").split(", ")) {
                if (checkVideo(c)) {
                    if (!video.contains(getId(channelUrl))) {
                        video.add(getId(channelUrl));
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(new Color(0xFF0000));
                        eb.setTitle(Language.getMessage("YouTube.TITLE"), null);
                        eb.setDescription(getTitle(channelUrl));
                        eb.addField(Language.getMessage("YouTube.DESCRIPTION"), getDescription(channelUrl), false);
                        eb.setAuthor((Language.getMessage("YouTube.Video.POSTED_VIDEO")).replace("%CHANNEL%", getName(channelUrl)));
                        eb.setImage(getThumbnail(channelUrl));
                        Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
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

        String jsonText = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonText);

        return json.getJSONArray("items").length() > 0;
    }


    static void check() throws IOException {
        if (ConfigHandler.containsProperty("YouTube API key") && ConfigHandler.containsProperty("YouTube channels") && ConfigHandler.getProperty("YouTube live notifications").equalsIgnoreCase("true")) {
            for (String c : ConfigHandler.getProperty("YouTube channels").split(", ")) {
                if (checkIfOnline(c)) {
                    if (!liveYoutube.contains(c)) {
                        liveYoutube.add(c);
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(new Color(0xFF0000));
                        eb.setTitle(Language.getMessage("YouTube.TITLE"), null);
                        eb.setDescription(getTitle(channelUrl));
                        eb.addField(Language.getMessage("YouTube.DESCRIPTION"), getDescription(channelUrl), false);
                        eb.setAuthor((Language.getMessage("YouTube.Live.WENT_LIVE")).replace("%CHANNEL%", getName(channelUrl)));
                        eb.setImage(getThumbnail(channelUrl));
                        Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                    }
                } else {
                    liveYoutube.remove(c);
                }
            }
        }
    }

}
