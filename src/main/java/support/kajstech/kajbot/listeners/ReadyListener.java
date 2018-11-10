package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.KajbotLogger;


public class ReadyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event) {

        KajbotLogger.info(KajbotLogger.bot, "Logged in using token: " + ConfigManager.getProperty("token"));
        KajbotLogger.info(KajbotLogger.bot, "Bot owner ID: " + ConfigManager.getProperty("ownerid"));
        KajbotLogger.info(KajbotLogger.bot, "Using command prefix: " + ConfigManager.getProperty("prefix"));
        KajbotLogger.info(KajbotLogger.bot, "Current ping: " + event.getJDA().getPing() + "ms");

    }

}
