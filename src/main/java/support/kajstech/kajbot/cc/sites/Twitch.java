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

public class Twitch {

    private static String channelUrl;

    public static boolean checkIfOnline(String channel) throws IOException {
        channelUrl = "https://api.twitch.tv/kraken/streams/" + channel + "?client_id=" + ConfigManager.getConfig().getProperty("Twitch client ID");

        String jsonText = readFromUrl(channelUrl);// reads text from URL
        JSONObject json = new JSONObject(jsonText);

        return !json.isNull("stream");
    }

    private static String readFromUrl(String url) throws IOException {
        URL page = new URL(url);
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(
                page.openStream(), StandardCharsets.UTF_8)).lines()) {
            return stream.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static String getGame() throws IOException {
        String jsonChannels = readFromUrl(channelUrl);
        JSONObject json = new JSONObject(jsonChannels);

        return json.getJSONObject("stream").getString("game");
    }

}
