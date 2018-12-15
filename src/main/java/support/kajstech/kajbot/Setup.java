package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Setup extends ListenerAdapter {


    static void setUp() throws IOException {

        if (!ConfigManager.containsProperty("Bot token")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert bot token: ");
            ConfigManager.setProperty("Bot token", br.readLine());
        }

        if (!ConfigManager.containsProperty("Command prefix")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            ConfigManager.setProperty("Command prefix", br.readLine());
        }

        if (!ConfigManager.containsProperty("Bot owner ID")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            ConfigManager.setProperty("Bot owner ID", br.readLine());
        }

        if (!ConfigManager.containsProperty("Bot controller role")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            ConfigManager.setProperty("Bot controller role", br.readLine());
        }

        if (!ConfigManager.containsProperty("API port")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("API port: ");
            ConfigManager.setProperty("API port", br.readLine());
        }

    }

}