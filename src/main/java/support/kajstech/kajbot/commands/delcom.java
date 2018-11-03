package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.CustomCommandsManager;

public class delcom extends Command {

    public delcom() {
        this.name = "delcom";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\W+");
        if (args[0].length() < 1) return;
        if (args[0].equalsIgnoreCase("addcom") || args[0].equalsIgnoreCase("delcom") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("eval") || args[0].equalsIgnoreCase("game") || args[0].equalsIgnoreCase("status"))
            return;

        if (CustomCommandsManager.cmds.containsKey(args[0])) {
            CustomCommandsManager.removeCommand(args[0]);
            e.getChannel().sendMessage("The command '``" + args[0].toUpperCase() + "``' has been deleted").queue();
        }

    }
}
