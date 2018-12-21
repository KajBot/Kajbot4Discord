package support.kajstech.kajbot;

import support.kajstech.kajbot.cc.StreamAndVideoChecker;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.web.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //CONFIG
        ConfigHandler.loadCfg();

        //LANGUAGE
        Language.init();

        try {
            Setup.setUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //API
        new Thread(() -> {
            try {
                Server.run(Integer.parseInt(ConfigHandler.getProperty("API port")));
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
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigHandler::storeCfg, "Shutdown-thread"));
    }

}
