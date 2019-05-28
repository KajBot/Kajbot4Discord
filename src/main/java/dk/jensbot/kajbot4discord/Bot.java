package dk.jensbot.kajbot4discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.mdkt.compiler.InMemoryJavaCompiler;
import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandManager;
import dk.jensbot.kajbot4discord.command.CustomCommandsHandler;
import dk.jensbot.kajbot4discord.notifications.Checker;
import dk.jensbot.kajbot4discord.utils.Config;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Bot {

    public static JDA jda;

    public static void run() throws LoginException, IllegalAccessException, InstantiationException, IOException, InterruptedException {

        //JDA Builder
        JDABuilder builder = new JDABuilder(AccountType.BOT);


        builder.setToken(Config.cfg.get("Bot-token"));
        builder.setGame(Game.playing(Config.cfg.get("Bot-game")));

        //Internal commands
        for (Class<? extends Command> command : Main.internalCommands) {
            CommandManager.addCommand(command.newInstance());
        }

        //Simple custom commands
        CustomCommandsHandler.getCustomCommands().forEach((k, v) -> CommandManager.addCustomCommand(k.toString(), v.toString()));

        //Java custom commands
        File dir = new File(System.getProperty("user.dir") + "/commands");
        if (!dir.exists()) Files.createDirectory(dir.toPath());
        for (File clazz : Objects.requireNonNull(dir.listFiles())) {

            String name = clazz.getName();
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf == -1 || !name.substring(lastIndexOf).equals(".java")) continue;

            try {
                StringBuilder classCode = new StringBuilder();
                Files.readAllLines(Paths.get(clazz.getAbsolutePath()), StandardCharsets.UTF_8).forEach(s -> classCode.append(s).append("\n"));
                CommandManager.addCommand((Command) InMemoryJavaCompiler.newInstance().compile(name.replace(".java", ""), classCode.toString()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Add listeners using ListenerAdaper
        for (Class<? extends ListenerAdapter> listener : Main.listeners) {
            builder.addEventListener(listener.newInstance());
        }

        //Builder settings
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setAudioEnabled(false);

        //Build JDA
        jda = builder.build().awaitReady();

        //NOTIFICATIONS
        new Thread(() -> {
            try {
                Checker.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
