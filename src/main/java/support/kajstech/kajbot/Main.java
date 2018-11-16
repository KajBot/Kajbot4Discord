package support.kajstech.kajbot;

import support.kajstech.kajbot.cc.StreamAndVideoChecker;
import support.kajstech.kajbot.utils.ConfigManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //CONFIG
        ConfigManager.init();

        //LANGUAGE
        Language.run();

        try {
            Setup.setUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //API
        new Thread(() -> {
            try {
                Server.run(ConfigManager.getProperty("Server port"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //BOT
        new Thread(() -> {
            try {
                Bot.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //CC
        new Thread(() -> {
            try {
                StreamAndVideoChecker.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //SHUTDOWN HOOK
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::shutdown, "Shutdown-thread"));
    }

}
