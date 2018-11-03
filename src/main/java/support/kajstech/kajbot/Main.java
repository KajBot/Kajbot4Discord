package support.kajstech.kajbot;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.reflections.Reflections;
import support.kajstech.kajbot.utils.*;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Set;

public class Main {


    public static JDA jda;
    public static CommandClient commandClient;

    public static void main(String[] args) throws IOException, LoginException {

        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::shutdown, "Shutdown-thread"));

        ConfigManager.init();
        CustomCommandsManager.init();

        //JDA Builder
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //CommandClient builder
        CommandClientBuilder ccBuilder = new CommandClientBuilder();

        //Setting Bot owner ID
        if (!ConfigManager.getConfig().stringPropertyNames().contains("ownerid")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot owner ID: ");
            token = br.readLine();
            ConfigManager.setProperty("ownerid", token);
        }

        //Setting Bot controller role
        if (!ConfigManager.getConfig().stringPropertyNames().contains("botcontroller")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Bot controller role: ");
            token = br.readLine();
            ConfigManager.setProperty("botcontroller", token);
        }

        ccBuilder.setOwnerId(ConfigManager.getProperty("ownerid"));
        ccBuilder.useHelpBuilder(false);
        ccBuilder.setGame(Game.playing("Kajbot"));

        //Setting bot token
        if (!ConfigManager.getConfig().stringPropertyNames().contains("token")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert bot token: ");
            token = br.readLine();
            ConfigManager.setProperty("token", token);
        }
        builder.setToken(ConfigManager.getProperty("token"));

        //Setting command prefix
        if (!ConfigManager.getConfig().stringPropertyNames().contains("prefix")) {
            String token;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Command prefix: ");
            token = br.readLine();
            ConfigManager.setProperty("prefix", token);
        }
        ccBuilder.setPrefix(ConfigManager.getProperty("prefix"));


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


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            switch (br.readLine()) {
                case "quit":
                case "shutdown":
                case "stop":
                case "exit": {
                    KajbotLogger.info("Shutting down...");
                    jda.shutdown();
                    return;
                }
                case "ping":
                case "mem":
                case "stats":
                case "debug": {
                    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
                    long totalMb = Runtime.getRuntime().totalMemory() / (1024 * 1024);
                    long usedMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
                    double cpuUsage = osBean.getSystemCpuLoad() * 100;
                    DecimalFormat df = new DecimalFormat("#.##");
                    KajbotLogger.info("Statistics\nCpu usage: " + df.format(cpuUsage) + "%\nMemory: " + usedMb + "MB/" + totalMb + "MB\nPing: " + jda.getPing() + "ms");
                    break;
                }
                case "test": {
                    System.out.println("test");
                }
            }

        }
    }
}
