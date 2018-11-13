package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Setup extends ListenerAdapter {

    private static Properties config = ConfigManager.getConfig();


    public static void setUp() throws IOException {

        if (!config.stringPropertyNames().contains("token")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert bot token: ");
            token = br.readLine();
            config.setProperty("token", token);
        }

        if (!config.stringPropertyNames().contains("prefix")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            token = br.readLine();
            config.setProperty("prefix", token);
        }

        if (!config.stringPropertyNames().contains("ownerid")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            token = br.readLine();
            config.setProperty("ownerid", token);
        }

        if (!config.stringPropertyNames().contains("botcontroller")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            token = br.readLine();
            config.setProperty("botcontroller", token);
        }

        if (!config.stringPropertyNames().contains("serverport")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server port: ");
            token = br.readLine();
            config.setProperty("serverport", token);
        }

        if (!config.stringPropertyNames().contains("modlog")) {
            config.setProperty("modlog", " ");
        }

        if (!config.stringPropertyNames().contains("linkblacklist")) {
            config.setProperty("linkblacklist", "false");
        }

        ConfigManager.setProperty("firsttimesetup", "false");

    }

}
