package dk.jensbot.kajbot4discord;

import dk.jensbot.kajbot4discord.web.Servlet;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;
import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CustomCommandsHandler;
import dk.jensbot.kajbot4discord.handlers.KeywordHandler;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.web.JettyServer;

import java.util.Set;

public class Main {
    public static final Set<Class<? extends Servlet>> servlets = new Reflections("dk.jensbot.kajbot4discord.web.context").getSubTypesOf(Servlet.class);
    public static final Set<Class<? extends ListenerAdapter>> listeners = new Reflections("dk.jensbot.kajbot4discord.listeners").getSubTypesOf(ListenerAdapter.class);
    public static final Set<Class<? extends Command>> internalCommands = new Reflections("dk.jensbot.kajbot4discord.command.commands").getSubTypesOf(Command.class);

    public static void main(String[] args) {

        //BOT
        new Thread(() -> {
            try {
                Bot.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //API/WEB
        new Thread(() -> {
            try {
                if(Config.cfg.get("API.enabled").equalsIgnoreCase("true")) {
                    JettyServer.run();
                }
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
