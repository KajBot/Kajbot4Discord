package support.kajstech.kajbot;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.web.JettyServer;
import support.kajstech.kajbot.web.Servlet;

import java.util.Set;

public class Main {
    public static final Set<Class<? extends Servlet>> servlets = new Reflections("support.kajstech.kajbot.web.context").getSubTypesOf(Servlet.class);
    public static final Set<Class<? extends ListenerAdapter>> listeners = new Reflections("support.kajstech.kajbot.listeners").getSubTypesOf(ListenerAdapter.class);
    public static final Set<Class<? extends Command>> internalCommands = new Reflections("support.kajstech.kajbot.command.commands").getSubTypesOf(Command.class);

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
                JettyServer.run();
                //Server_old.run();
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
