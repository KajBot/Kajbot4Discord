package support.kajstech.kajbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.reflections.Reflections;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandManager;
import support.kajstech.kajbot.command.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.ConfigHandler;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Bot {

    public static JDA jda;

    private static Reflections cmdReflections = new Reflections("support.kajstech.kajbot.command.commands");
    public static final Set<Class<? extends Command>> internalCommands = cmdReflections.getSubTypesOf(Command.class);

    static void run() throws LoginException, IllegalAccessException, InstantiationException, IOException {

        //JDA Builder
        JDABuilder builder = new JDABuilder(AccountType.BOT);


        builder.setToken(ConfigHandler.getProperty("Bot token"));
        builder.setGame(Game.playing(ConfigHandler.getProperty("Bot game")));

        //Adding commands
        for (Class<? extends Command> command : internalCommands) {
            CommandManager.addCommand(command.newInstance());
        }

        //Simple custom commands
        CustomCommandsHandler.getCustomCommands().forEach((k, v) -> CommandManager.addCustomCommand(k.toString(), v.toString()));

        //External custom commands
        File dir = new File(System.getProperty("user.dir") + "/commands");
        if (!dir.exists()) Files.createDirectory(dir.toPath());
        for (File clazz : Objects.requireNonNull(dir.listFiles())) {

            String name = clazz.getName();
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf == -1) {
                continue;
            }
            if (!name.substring(lastIndexOf).equals(".java")) continue;
            try {
                StringBuilder classCode = new StringBuilder();
                try (Stream<String> stream = Files.lines(Paths.get(clazz.getAbsolutePath()), StandardCharsets.UTF_8)) {
                    stream.forEach(s -> classCode.append(s).append("\n"));
                }

                Class<?> command = InMemoryJavaCompiler.newInstance().compile(clazz.getName().replace(".java", ""), classCode.toString());
                CommandManager.addCommand((Command) command.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Adding listeners using ListenerAdaper
        Reflections listenerReflections = new Reflections("support.kajstech.kajbot.listeners");
        Set<Class<? extends ListenerAdapter>> allListeners = listenerReflections.getSubTypesOf(ListenerAdapter.class);
        for (Class<? extends ListenerAdapter> listener : allListeners) {
            builder.addEventListener(listener.newInstance());
        }

        //Builder settings
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setAudioEnabled(false);

        //Building JDA
        jda = builder.build();
    }
}
