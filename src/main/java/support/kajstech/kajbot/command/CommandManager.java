package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.reflections.Reflections;
import support.kajstech.kajbot.utils.Config;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    public static final Set<Class<? extends Command>> internalCommands = new Reflections("support.kajstech.kajbot.command.commands").getSubTypesOf(Command.class);

    private static final Map<String, Command> commands = new HashMap<>();


    public static void addCommand(Command command) {
        if (!commands.containsKey(command.name)) {
            commands.put(command.name, command);
        }
    }

    public static void addCustomCommand(String key, String value) {
        for (Class<? extends Command> command : internalCommands) {
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
        for (Class<? extends Command> command : internalCommands) {
            if (command.getSimpleName().equalsIgnoreCase(key)) return;
        }
        commands.remove(key);
        CustomCommandsHandler.getCustomCommands().remove(key);
    }

    public void handleCommand(MessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(Config.get("Command prefix")), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> argsSplit = Arrays.asList(split).subList(1, split.length);
            final String args = String.join(" ", argsSplit);

            commands.get(invoke).run(new CommandEvent(argsSplit, args, event));
        }
    }
}
