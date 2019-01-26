package support.kajstech.kajbot;

import support.kajstech.kajbot.cc.StreamAndVideoChecker;
import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.web.Server;

public class Main {

    public static void main(String[] args) {

        //BOT SETUP
        Setup.setUp();

        //API/WEB
        new Thread(() -> {
            try {
                Server.run();
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

        //YT/TWITCH
        new Thread(() -> {
            try {
                if (!ConfigHandler.containsProperty("Notification channel ID")) return;
                StreamAndVideoChecker.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //SHUTDOWN HOOK
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigHandler::saveCfg, "Config-shutdown-thread"));
        Runtime.getRuntime().addShutdownHook(new Thread(CustomCommandsHandler::saveCustomCommands, "CustomCommands-shutdown-thread"));
        Runtime.getRuntime().addShutdownHook(new Thread(KeywordHandler::saveKeywords, "Keyword-shutdown-thread"));
    }

}
