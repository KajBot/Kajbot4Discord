package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.CustomCommandsManager;


public class command extends Command {

    public command() {
        this.name = "command";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split(" ");
        switch (args[0]) {
            case "del":
            case "remove":
                try {
                    if (CustomCommandsManager.cmds.containsKey(args[1])) {
                        CustomCommandsManager.removeCommand(args[1]);
                        e.getChannel().sendMessage("The command '``" + args[1].toUpperCase() + "``' has been deleted").queue();
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String cmdName = args[1];
                    String[] cmdContext = e.getArgs().substring(cmdName.length() + "add ".length() + 1).split(" ");
                    CustomCommandsManager.addCommand(cmdName, String.join(" ", cmdContext));
                    e.getChannel().sendMessage("'``" + cmdName.toUpperCase() + "``' is now registered as a command").queue();
                } catch (Exception ignored) {
                    return;
                }

                break;
        }

    }


}
