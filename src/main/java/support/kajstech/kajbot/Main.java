package support.kajstech.kajbot;

import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.Config;
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

        //SHUTDOWN HOOK
        Runtime.getRuntime().addShutdownHook(new Thread(Config.cfg::save, "Config-shutdown-thread"));
        Runtime.getRuntime().addShutdownHook(new Thread(CustomCommandsHandler::saveCustomCommands, "CustomCommands-shutdown-thread"));
        Runtime.getRuntime().addShutdownHook(new Thread(KeywordHandler::saveKeywords, "Keyword-shutdown-thread"));


    }

}
