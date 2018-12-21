package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;


public class command extends Command {

    public command() {
        this.name = "command";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
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
                        e.getChannel().sendMessage((Language.messages.getProperty("Command.UNREGISTERED")).replace("%CMD%", args[1].toUpperCase())).queue();
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
                    e.getChannel().sendMessage((Language.messages.getProperty("Command.REGISTERED")).replace("%CMD%", cmdName.toUpperCase())).queue();
                } catch (Exception ignored) {
                    return;
                }

                break;
        }

    }


}
