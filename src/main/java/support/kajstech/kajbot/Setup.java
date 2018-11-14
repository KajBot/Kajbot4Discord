package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Setup extends ListenerAdapter {

    private static Properties config = ConfigManager.getConfig();


    static void setUp() throws IOException {

        if (!config.stringPropertyNames().contains("Bot token")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert bot token: ");
            token = br.readLine();
            config.setProperty("Bot token", token);
        }

        if (!config.stringPropertyNames().contains("Command prefix")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            token = br.readLine();
            config.setProperty("Command prefix", token);
        }

        if (!config.stringPropertyNames().contains("Bot owner ID")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            token = br.readLine();
            config.setProperty("Bot owner ID", token);
        }

        if (!config.stringPropertyNames().contains("Bot controller role")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            token = br.readLine();
            config.setProperty("Bot controller role", token);
        }

        if (!config.stringPropertyNames().contains("Server port")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server port: ");
            token = br.readLine();
            config.setProperty("Server port", token);
        }

        if (!config.stringPropertyNames().contains("Twitch client ID")) {
            config.setProperty("Twitch client ID", " ");
        }

        if (!config.stringPropertyNames().contains("Twitch channels")) {
            config.setProperty("Twitch channels", " ");
        }

        if (!config.stringPropertyNames().contains("YouTube channel ID")) {
            config.setProperty("YouTube channel ID", " ");
        }

        if (!config.stringPropertyNames().contains("YouTube API key")) {
            config.setProperty("YouTube API key", " ");
        }

        if (!config.stringPropertyNames().contains("Link blacklist")) {
            config.setProperty("Link blacklist", "false");
        }

    }

}
