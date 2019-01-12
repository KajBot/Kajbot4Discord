package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandManager {

    private static final Map<String, ICommand> commands = new HashMap<>();


    public static void addCommand(ICommand command) {
        if (!commands.containsKey(command.getName())) {
            commands.put(command.getName(), command);
        }
    }

    public void handleCommand(MessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(ConfigHandler.getProperty("Command prefix")), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            commands.get(invoke).handle(args, event);
        }
    }
}
