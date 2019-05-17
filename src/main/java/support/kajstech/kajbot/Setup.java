package support.kajstech.kajbot;

import support.kajstech.kajbot.utils.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

class Setup {
    static void setUp() {

        if (!Config.cfg.contains("Bot token")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot token: ");
            try {
                Config.cfg.set("Bot token", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Config.cfg.contains("Bot owner ID")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.print("Bot owner ID: ");
            try {
                Config.cfg.set("Bot owner ID", br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Config.cfg.contains("API token")) {
            Config.cfg.set("API token", UUID.randomUUID().toString());
        }
        if (!Config.cfg.contains("API port")) {
            Config.cfg.set("API port", "1337");
        }

    }

}