package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.KeywordManager;

public class addkeyword extends Command {

    public addkeyword() {
        this.name = "addkeyword";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args;

        args = e.getArgs().split("\\W+");
        if (args[0].length() < 1) return;

        String kwName = args[0];
        try {
            args = e.getArgs().substring(kwName.length() + 1).split("\\W+");
        } catch (Exception ignored) {
            return;
        }


        KeywordManager.addKeyword(kwName, String.join(" ", args));
        e.getChannel().sendMessage("'``" + kwName.toUpperCase() + "``' is now registered as a keyword").queue();


    }
}
