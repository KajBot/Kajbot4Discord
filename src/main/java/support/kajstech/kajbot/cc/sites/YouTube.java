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

public class YouTube {

    public static void checkYouTube() {
        YouTubeLive.checkYouTube();
        YouTubeVideo.checkYouTube();
    }
}

class YouTubeVideo {

    private static String channelUrl;

    private static List<String> video = new ArrayList<>();

    private static boolean checkVideo(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&maxResults=1&channelId=" + channel + "&key=" + ConfigHandler.getProperty("YouTube API key");

        String jsonText = readFromUrl(channelUrl);// reads text from URL
        JSONObject json = new JSONObject(jsonText);

        return json.getJSONArray("items").length() > 0;
    }

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(
                page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private static String getTitle() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
    }

    private static String getId() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    public static String getName() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    private static String getThumbnail() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
    }


    static void checkYouTube() {
        try {
            if (ConfigHandler.getProperty("YouTube API key").length() > 1 && ConfigHandler.getProperty("YouTube channels").length() > 1) {
                for (String c : ConfigHandler.getProperty("YouTube channels").split(", ")) {
                    if (checkVideo(c)) {
                        if (!video.contains(getId())) {
                            video.add(getId());
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setColor(new Color(0xA6C055));
                            eb.setTitle("Title", null);
                            eb.setDescription(getTitle());
                            eb.setAuthor(getName() + " just posted a video on YouTube!", "https://youtu.be/" + getId(), null);
                            eb.setImage(getThumbnail());
                            Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                        }
                    } else {
                        video.remove(getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class YouTubeLive {
    private static List<String> liveYoutube = new ArrayList<>();

    private static String channelUrl;

    private static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&eventType=live&maxResults=1&channelId=" + channel + "&key=" + ConfigHandler.getProperty("YouTube API key");

        String jsonText = readFromUrl(channelUrl);// reads text from URL
        JSONObject json = new JSONObject(jsonText);

        return json.getJSONArray("items").length() > 0;
    }

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(
                page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private static String getTitle() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
    }

    private static String getId() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    public static String getName() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    private static String getThumbnail() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
    }


    static void checkYouTube() {
        try {
            if (ConfigHandler.getProperty("YouTube API key").length() > 1 && ConfigHandler.getProperty("YouTube channels").length() > 1) {
                for (String c : ConfigHandler.getProperty("YouTube channels").split(", ")) {
                    if (checkIfOnline(c)) {
                        if (!liveYoutube.contains(c)) {
                            liveYoutube.add(c);
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setColor(new Color(0xA6C055));
                            eb.setTitle("Title", null);
                            eb.setDescription(getTitle());
                            eb.setAuthor(getName() + " just went live on YouTube!", "https://youtu.be/" + getId(), null);
                            eb.setImage(getThumbnail());
                            Bot.jda.getTextChannelById(ConfigHandler.getProperty("Notification channel ID")).sendMessage(eb.build()).queue();
                        }
                    } else {
                        liveYoutube.remove(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
