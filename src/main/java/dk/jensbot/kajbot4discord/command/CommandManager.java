package dk.jensbot.kajbot4discord.command;

import dk.jensbot.kajbot4discord.Bot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import dk.jensbot.kajbot4discord.Main;
import dk.jensbot.kajbot4discord.utils.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandManager {

    private static final Map<String, Command> commands = new HashMap<>();

    public static void addCommand(Command command) {
        if (!commands.containsKey(command.name)) {
            commands.put(command.name, command);
        }
    }

    public static void addCustomCommand(String key, String value) {
        for (Class<? extends Command> command : Main.internalCommands) {
            if (command.getSimpleName().equalsIgnoreCase(key)) return;
        }
        commands.put(key, new Command() {
            @Override
            protected void execute(CommandEvent e) {
                String message = value
                        .replace(">USER<", e.getAuthor().getAsMention())
                        .replace(">MEMBERCOUNT<", String.valueOf(e.getGuild().getMembers().size()));
                e.reply(message);
            }
        });
        CustomCommandsHandler.getCustomCommands().setProperty(key, value);
    }

    public static void removeCustomCommand(String key) {
        for (Class<? extends Command> command : Main.internalCommands) {
            if (command.getSimpleName().equalsIgnoreCase(key)) return;
        }
        commands.remove(key);
        CustomCommandsHandler.getCustomCommands().remove(key);
    }

    public void handleCommand(MessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(Config.cfg.get("Bot.prefix")), "").replaceFirst(Bot.jda.getSelfUser().getAsMention() + " ", "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> argsSplit = Arrays.asList(split).subList(1, split.length);
            final String args = String.join(" ", argsSplit);

            commands.get(invoke).run(new CommandEvent(argsSplit, args, event));
        }
    }
}
