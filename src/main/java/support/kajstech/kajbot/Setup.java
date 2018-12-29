package support.kajstech.kajbot;

import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

class Setup {
    static void setUp() {

        if (!ConfigHandler.containsProperty("Bot token")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot token: ");
            try {
                ConfigHandler.setProperty("Bot token", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ConfigHandler.containsProperty("Command prefix")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Command prefix: ");
            try {
                ConfigHandler.setProperty("Command prefix", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ConfigHandler.containsProperty("Bot owner ID")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot owner ID: ");
            try {
                ConfigHandler.setProperty("Bot owner ID", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ConfigHandler.containsProperty("Bot controller role")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot controller role: ");
            try {
                ConfigHandler.setProperty("Bot controller role", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ConfigHandler.containsProperty("API port")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("API port: ");
            try {
                ConfigHandler.setProperty("API port", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ConfigHandler.containsProperty("API token")) {
            ConfigHandler.setProperty("API token", UUID.randomUUID().toString());
        }

    }

}