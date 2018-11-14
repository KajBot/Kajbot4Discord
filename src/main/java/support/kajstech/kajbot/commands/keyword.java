package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.KeywordManager;


public class keyword extends Command {

    public keyword() {
        this.name = "keyword";
        this.guildOnly = true;
        this.requiredRole = ConfigManager.getProperty("Bot controller role");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split(" ");
        switch (args[0]) {
            case "del":
            case "remove":
                try {
                    if (KeywordManager.kws.containsKey(args[1])) {
                        KeywordManager.removeKeyword(args[1]);
                        e.getChannel().sendMessage("The keyword '``" + args[1].toUpperCase() + "``' has been deleted").queue();
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String kwName = args[1];
                    String[] kwContext = e.getArgs().substring(kwName.length() + "add ".length() + 1).split(" ");
                    KeywordManager.addKeyword(kwName, String.join(" ", kwContext));
                    e.getChannel().sendMessage("'``" + kwName.toUpperCase() + "``' is now registered as a keyword").queue();
                } catch (Exception ignored) {
                    return;
                }
                break;
        }

    }


}
