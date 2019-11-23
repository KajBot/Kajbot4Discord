package dk.jensbot.kajbot4discord;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandManager;
import dk.jensbot.kajbot4discord.command.CustomCommandHandler;
import dk.jensbot.kajbot4discord.listeners.Ready;
import dk.jensbot.kajbot4discord.notifications.Checker;
import dk.jensbot.kajbot4discord.utils.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.mdkt.compiler.InMemoryJavaCompiler;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Bot {

    public static JDA jda;

    public static void run() throws LoginException, IllegalAccessException, InstantiationException, IOException {

        //JDA Builder
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();

        builder.setToken(Config.cfg.get("Bot.token"));
        builder.setActivity(Activity.playing(Config.cfg.get("Bot.game")));

        //Internal commands
        for (Class<? extends Command> command : Main.internalCommands) {
            CommandManager.addCommand(command.newInstance());
        }

        //Simple custom commands
        CustomCommandHandler.getCommands().forEach((k, v) -> CommandManager.addCustomCommand(k.toString(), v.toString()));

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
            builder.addEventListeners(listener.newInstance());
        }

        //Builder settings
        builder.setBulkDeleteSplittingEnabled(false);

        //Sharding
        builder.setShardsTotal(4);

        //Build JDA
        builder.build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
