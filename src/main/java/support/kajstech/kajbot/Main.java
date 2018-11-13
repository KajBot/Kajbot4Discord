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
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.CustomCommandsManager;
import support.kajstech.kajbot.utils.KeywordManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class Main {

    public static JDA jda;
    public static CommandClient commandClient;

    public static void main(String[] args) throws IOException {

        ConfigManager.init();
        KeywordManager.init();
        CustomCommandsManager.init();
        Setup.setUp();

        new Thread(() -> {
            try {
                Server.run(ConfigManager.getProperty("serverport"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                runBot();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::shutdown, "Shutdown-thread"));
    }


    private static void runBot() throws Exception {

        //JDA Builder
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //CommandClient builder
        CommandClientBuilder ccBuilder = new CommandClientBuilder();


        builder.setToken(ConfigManager.getProperty("token"));
        ccBuilder.setPrefix(ConfigManager.getProperty("prefix"));
        ccBuilder.setOwnerId(ConfigManager.getProperty("ownerid"));

        ccBuilder.useHelpBuilder(false);
        ccBuilder.setGame(Game.playing("Kajbot"));


        //Loading commands
        Reflections cmdReflections = new Reflections("support.kajstech.kajbot.commands");
        Set<Class<? extends Command>> allCommands = cmdReflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> command : allCommands) {
            try {
                ccBuilder.addCommand(command.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //Adding event listeners using ListenerAdaper
        Reflections listenerReflections = new Reflections("support.kajstech.kajbot.listeners");
        Set<Class<? extends ListenerAdapter>> allListeners = listenerReflections.getSubTypesOf(ListenerAdapter.class);
        for (Class<? extends ListenerAdapter> listener : allListeners) {
            try {
                builder.addEventListener(listener.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
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
        ConfigManager.getConfig().storeToXML(new FileOutputStream("config.xml"), null);
        jda = builder.build();
    }
}
