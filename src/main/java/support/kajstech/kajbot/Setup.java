package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

class Setup extends ListenerAdapter {


    static void setUp() throws IOException {


        if (!ConfigHandler.containsProperty("Bot token")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot token: ");
            ConfigHandler.setProperty("Bot token", br.readLine());
        }

        if (!ConfigHandler.containsProperty("Command prefix")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            ConfigHandler.setProperty("Command prefix", br.readLine());
        }

        if (!ConfigHandler.containsProperty("Bot owner ID")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            ConfigHandler.setProperty("Bot owner ID", br.readLine());
        }

        if (!ConfigHandler.containsProperty("Bot controller role")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            ConfigHandler.setProperty("Bot controller role", br.readLine());
        }

        if (!ConfigHandler.containsProperty("API port")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("API port: ");
            ConfigHandler.setProperty("API port", br.readLine());
        }

        if (!ConfigHandler.containsProperty("API token")) {
            ConfigHandler.setProperty("API token", UUID.randomUUID().toString());
        }

    }

}