package support.kajstech.kajbot;

import support.kajstech.kajbot.cc.StreamAndVideoChecker;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.web.Server;

public class Main {

    public static void main(String[] args) {


        //CONFIG
        ConfigHandler.loadCfg();

        //LANGUAGE
        Language.init();

        //BOT SETUP
        Setup.setUp();

        //API/WEB
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

        //YT/TWITCH
        new Thread(() -> {
            try {
                StreamAndVideoChecker.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //SHUTDOWN HOOK
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigHandler::saveCfg, "Shutdown-thread"));
    }

}
