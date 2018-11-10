package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.CustomCommandsManager;

public class addcom extends Command {

    public addcom() {
        this.name = "addcom";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\W+");
        if (args[0].length() < 1) return;
        if (args[0].equalsIgnoreCase("addcom") || args[0].equalsIgnoreCase("delcom") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("eval") || args[0].equalsIgnoreCase("game") || args[0].equalsIgnoreCase("status"))
            return;

        String cmdName = args[0];
        try {
            args = e.getArgs().substring(cmdName.length() + 1).split("\\W+");
        } catch (Exception ignored) {
            return;
        }


        CustomCommandsManager.addCommand(cmdName, String.join(" ", args));
        e.getChannel().sendMessage("'``" + cmdName.toUpperCase() + "``' is now registered as a command").queue();


    }
}
