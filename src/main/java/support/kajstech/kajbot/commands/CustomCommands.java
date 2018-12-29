package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;


public class CustomCommands extends Command {

    public CustomCommands() {
        this.name = "command";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split(" ");
        switch (args[0]) {
            case "del":
            case "remove":
                try {
                    if (CustomCommandsHandler.cmds.containsKey(args[1])) {
                        CustomCommandsHandler.removeCommand(args[1]);
                        e.reply((Language.getMessage("Command.UNREGISTERED")).replace("%CMD%", args[1].toUpperCase()));
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String cmdName = args[1];
                    String[] cmdContext = e.getArgs().substring(cmdName.length() + "add ".length() + 1).split(" ");
                    CustomCommandsHandler.addCommand(cmdName, String.join(" ", cmdContext));
                    e.reply((Language.getMessage("Command.REGISTERED")).replace("%CMD%", cmdName.toUpperCase()));
                } catch (Exception ignored) {
                    return;
                }

                break;
        }

    }


}
