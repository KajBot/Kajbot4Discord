package support.kajstech.kajbot;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Bot {

    public static JDA jda;
    public static CommandClient commandClient;

    static void run() {

        KeywordHandler.init();
        CustomCommandsHandler.init();
        //JDA Builder
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //CommandClient builder
        CommandClientBuilder ccBuilder = new CommandClientBuilder();


        builder.setToken(ConfigHandler.getProperty("Bot token"));
        ccBuilder.setPrefix(ConfigHandler.getProperty("Command prefix"));
        ccBuilder.setOwnerId(ConfigHandler.getProperty("Bot owner ID"));

        ccBuilder.useHelpBuilder(false);
        ccBuilder.setGame(Game.playing(ConfigHandler.getProperty("Bot game")));


        //Loading commands
        Reflections cmdReflections = new Reflections("support.kajstech.kajbot.commands");
        Set<Class<? extends Command>> allCommands = cmdReflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> command : allCommands) {
            try {
                ccBuilder.addCommand(command.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        //Adding event listeners using ListenerAdaper
        Reflections listenerReflections = new Reflections("support.kajstech.kajbot.listeners");
        Set<Class<? extends ListenerAdapter>> allListeners = listenerReflections.getSubTypesOf(ListenerAdapter.class);
        for (Class<? extends ListenerAdapter> listener : allListeners) {
            try {
                builder.addEventListener(listener.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        //Building CommandClient
        commandClient = ccBuilder.build();

        //Adding CommandClient listener
        builder.addEventListener(commandClient);

        //Random builder settings
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setAudioEnabled(false);

        //Building JDA
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
