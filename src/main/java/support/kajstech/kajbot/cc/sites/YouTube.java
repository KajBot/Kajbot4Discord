package support.kajstech.kajbot.cc.sites;

import org.json.JSONObject;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YouTube {

    private static String channelUrl;

    public static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video&eventType=live&maxResults=1&channelId=" + channel + "&key=" + ConfigManager.getConfig().getProperty("YouTube API key");

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

    public static String getTitle() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
    }

    public static String getId() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }

    public static String getName() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle");
    }

    public static String getThumbnail() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
    }

}
