package support.kajstech.kajbot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandManager;
import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.utils.ClassHelper;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

public class Bot {

    public static JDA jda;

    private static Reflections cmdReflections = new Reflections("support.kajstech.kajbot.command.commands");
    public static final Set<Class<? extends Command>> internalCommands = cmdReflections.getSubTypesOf(Command.class);

    static void run() throws LoginException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, ClassNotFoundException {

        //JDA Builder
        JDABuilder builder = new JDABuilder(AccountType.BOT);


        builder.setToken(ConfigHandler.getProperty("Bot token"));
        builder.setActivity(Activity.playing(ConfigHandler.getProperty("Bot game")));

        //Adding commands
        for (Class<? extends Command> command : internalCommands) {
            CommandManager.addCommand(command.getDeclaredConstructor().newInstance());
        }

        //Simple custom commands
        CustomCommandsHandler.getCustomCommands().forEach((k, v) -> CommandManager.addCustomCommand(k.toString(), v.toString()));

        //External custom commands
        File dir = new File(System.getProperty("user.dir") + "\\commands");
        if (!dir.exists()) Files.createDirectory(dir.toPath());
        for (File clazz : Objects.requireNonNull(dir.listFiles())) {
            boolean compile = false;
            if (ClassHelper.getFileExtension(clazz).equals(".java")) compile = true;

            CommandManager.addCommand((Command) ClassHelper.loadClass(new File(dir + "\\" + clazz.getName()), Command.class, compile).getDeclaredConstructor().newInstance());
        }


        //Adding listeners using ListenerAdaper
        Reflections listenerReflections = new Reflections("support.kajstech.kajbot.listeners");
        Set<Class<? extends ListenerAdapter>> allListeners = listenerReflections.getSubTypesOf(ListenerAdapter.class);
        for (Class<? extends ListenerAdapter> listener : allListeners) {
            builder.addEventListeners(listener.getDeclaredConstructor().newInstance());
        }

        //Builder settings
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setAudioEnabled(false);

        //Building JDA
        jda = builder.build();
    }
}
