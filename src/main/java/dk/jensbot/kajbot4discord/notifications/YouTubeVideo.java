package dk.jensbot.kajbot4discord.notifications;

import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.Language;
import dk.jensbot.simplecfg.ConfigFactory;
import dk.jensbot.simplecfg.Format;
import dk.jensbot.simplecfg.SimpleCfg;
import net.dv8tion.jda.api.entities.ChannelType;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class YouTubeVideo {

    private static SimpleCfg postedVideos = new ConfigFactory("data/postedvideos").format(Format.XML).create();
    private static String channelUrl;

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(
                page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private static String getId() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    private static String getName() throws IOException {
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    private static boolean checkForVideos(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&maxResults=1&channelId=" + channel + "&key=" + Config.cfg.get("YouTube.key");
        return new JSONObject(readFromUrl(channelUrl)).getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId") != null && !postedVideos.hasKey(getId());
    }

    static void check() throws IOException {
        for (String c : Config.cfg.get("YouTube.channels").split(", ")) {
            if (checkForVideos(c)) {
                Bot.jda.getTextChannelById(Config.cfg.get("Notifications.channelID")).sendMessage((Language.lang.getProperty("YouTube.Video.POSTED_VIDEO")).replace("%CHANNEL%", getName()) + "  https://youtu.be/" + getId()).queue();
                postedVideos.set(getId(), "");
            }
        }
    }
}
