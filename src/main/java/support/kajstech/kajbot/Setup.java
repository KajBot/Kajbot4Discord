package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Setup extends ListenerAdapter {


    static void setUp() throws IOException {

        if (ConfigManager.getProperty("Bot token").isEmpty()) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert bot token: ");
            token = br.readLine();
            ConfigManager.setProperty("Bot token", token);
        }

        if (ConfigManager.getProperty("Command prefix").isEmpty()) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            token = br.readLine();
            ConfigManager.setProperty("Command prefix", token);
        }

        if (ConfigManager.getProperty("Bot owner ID").isEmpty()) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            token = br.readLine();
            ConfigManager.setProperty("Bot owner ID", token);
        }

        if (ConfigManager.getProperty("Bot controller role").isEmpty()) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            token = br.readLine();
            ConfigManager.setProperty("Bot controller role", token);
        }

        if (ConfigManager.getProperty("Server port").isEmpty()) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server port: ");
            token = br.readLine();
            ConfigManager.setProperty("Server port", token);
        }

    }

}
