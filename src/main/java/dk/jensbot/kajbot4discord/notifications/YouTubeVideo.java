package dk.jensbot.kajbot4discord.notifications;

import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;
import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class YouTubeVideo {

    private static SimpleCfg postedVideos = new ConfigFactory("data/postedvideos").format(Format.XML).create();
    private static String channelUrl;

    private static JSONObject readFromUrl(String url) throws IOException {
        JSONObject responseJson;
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

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

    private static String getId() throws IOException {
        return readFromUrl(channelUrl).getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    private static String getName() throws IOException {
        return readFromUrl(channelUrl).getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    private static boolean checkNewVideo(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&maxResults=1&channelId=" + channel + "&key=" + Config.cfg.get("YouTube.key");
        return !postedVideos.hasKey(getId());
    }

    static void check() throws IOException {
        for (String c : Config.cfg.get("YouTube.channels").split(", ")) {
            if (checkNewVideo(c)) {
                Bot.jda.getTextChannelById(Config.cfg.get("Notifications.channelID")).sendMessage((Language.lang.getProperty("YouTube.Video.POSTED_VIDEO")).replace("%CHANNEL%", getName()) + "  https://youtu.be/" + getId()).queue();
                postedVideos.set(getId(), "");
            }
        }
    }
}
