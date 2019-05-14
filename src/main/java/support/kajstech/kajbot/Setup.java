package support.kajstech.kajbot;

import support.kajstech.kajbot.utils.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

class Setup {
    static void setUp() {

        if (!Config.contains("Bot token")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot token: ");
            try {
                Config.set("Bot token", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Config.contains("Bot owner ID")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot owner ID: ");
            try {
                Config.set("Bot owner ID", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Config.contains("API token")) {
            Config.set("API token", UUID.randomUUID().toString());
        }

    }

}