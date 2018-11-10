package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.KeywordManager;

public class delkeyword extends Command {

    public delkeyword() {
        this.name = "delkeyword";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("botcontroller");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\W+");
        if (args[0].length() < 1) return;

        if (KeywordManager.kws.containsKey(args[0])) {
            KeywordManager.removeKeyword(args[0]);
            e.getChannel().sendMessage("The keyword '``" + args[0].toUpperCase() + "``' has been deleted").queue();
        }

    }
}
