package dk.jensbot.kajbot4discord;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.notifications.Checker;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.web.JettyServer;
import dk.jensbot.kajbot4discord.web.Servlet;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;

import java.util.Set;

public class Main {
    //REFLECTIONS
    public static final Set<Class<? extends Servlet>> servlets = new Reflections("dk.jensbot.kajbot4discord.web.context").getSubTypesOf(Servlet.class);
    public static final Set<Class<? extends ListenerAdapter>> listeners = new Reflections("dk.jensbot.kajbot4discord.listeners").getSubTypesOf(ListenerAdapter.class);
    public static final Set<Class<? extends Command>> internalCommands = new Reflections("dk.jensbot.kajbot4discord.command.commands").getSubTypesOf(Command.class);

    public static void main(String[] args) {

        //BOT
        new Thread(() -> {
            try {
                new Bot();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            Bot.botStarted.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //API/WEB
        new Thread(() -> {
            if (Config.cfg.get("API.enabled").equalsIgnoreCase("true")) {
                try {
                    JettyServer.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //NOTIFICATIONS
        new Thread(() -> {
            try {
                Checker.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
