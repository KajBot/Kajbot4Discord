package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Setup extends ListenerAdapter {


    static void setUp() throws IOException {

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Bot token")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert bot token: ");
            token = br.readLine();
            ConfigManager.setProperty("Bot token", token);
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Command prefix")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            token = br.readLine();
            ConfigManager.setProperty("Command prefix", token);
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Bot owner ID")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            token = br.readLine();
            ConfigManager.setProperty("Bot owner ID", token);
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Bot controller role")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            token = br.readLine();
            ConfigManager.setProperty("Bot controller role", token);
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Server port")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server port: ");
            token = br.readLine();
            ConfigManager.setProperty("Server port", token);
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Twitch client ID")) {
            ConfigManager.setProperty("Twitch client ID", " ");
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Twitch channels")) {
            ConfigManager.setProperty("Twitch channels", " ");
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("YouTube channel ID")) {
            ConfigManager.setProperty("YouTube channels", " ");
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("YouTube API key")) {
            ConfigManager.setProperty("YouTube API key", " ");
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Notifications channel")) {
            ConfigManager.setProperty("Notifications channel ID", " ");
        }

        if (!ConfigManager.getConfig().stringPropertyNames().contains("Link blacklist")) {
            ConfigManager.setProperty("Link blacklist", "false");
        }

    }

}
